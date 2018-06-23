package top.bootz.commons.snowflake;

import java.util.Calendar;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.base.Preconditions;

import top.bootz.commons.exception.BaseRuntimeException;

/**
 * 衍生于Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开): <br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 -
 * 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的twepoch属性）。41位的时间截，可以使用69年，年T
 * = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69 <br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
public class IdGenerator {

    /**
     * 起始时间戳，用于用当前时间戳减去这个时间戳，算出偏移量
     */
    private static final long EPOCH;

    /**
     * workerId占用的位数5（表示只允许workId的范围为：0-1023）
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * dataCenterId占用的位数：5
     **/
    private static final long DATACENTER_ID_BITS = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    public static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    /**
     * 支持的最大数据标识id，结果是31
     */
    public static final long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);

    /**
     * 序列在id中占的位数
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 机器ID向左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 是否使用时间戳优化
     */
    private boolean isClock = false;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }

    /**
     * 设置工作进程Id.
     *
     * @param workerId 工作进程Id
     */
    public void setWorkerId(final Long workerId) {
        Preconditions.checkArgument(workerId >= 0L && workerId < MAX_WORKER_ID,
                String.format("worker id can't be greater than %d or less than 0", MAX_WORKER_ID));
        this.workerId = workerId;
    }

    /**
     * 设置数据中心Id.
     *
     * @param dataCenterId 数据中心Id
     */
    public void setDataCenterId(final Long dataCenterId) {
        Preconditions.checkArgument(dataCenterId >= 0L && dataCenterId < MAX_DATACENTER_ID,
                String.format("dataCenter id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        this.dataCenterId = dataCenterId;
    }

    public void setClock(boolean clock) {
        isClock = clock;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 闰秒：如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    this.wait(offset << 1);
                    timestamp = this.timeGen();
                    if (timestamp < lastTimestamp) {
                        throw new BaseRuntimeException(String.format(
                                "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds",
                                lastTimestamp, timestamp));
                    }
                } catch (Exception e) {
                    throw new BaseRuntimeException(e);
                }
            } else {
                throw new BaseRuntimeException(String.format(
                        "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds",
                        lastTimestamp, timestamp));
            }
        }

        // 解决跨毫秒生成ID序列号始终为偶数的缺陷:如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            // 通过位与运算保证计算的结果范围始终是 0-4095
            if (0L == (sequence = ++sequence & SEQUENCE_MASK)) {
                timestamp = this.tilNextMillis(lastTimestamp);
            }
        } else {
            /*
             * 时间戳改变，毫秒内序列重置，这里之所以生成随机数，是为了保证id末尾一个数字在0-9上分布均匀，
             * 数据库分库分表时，不至于出现数据分布不均匀
             */
            sequence = RandomUtils.nextLong(0, 9);
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT) | (dataCenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        if (isClock) {
            // 解决高并发下获取时间戳的性能问题
            return SystemClock.now();
        } else {
            return System.currentTimeMillis();
        }
    }

    // 测试
	/*public static void main(String[] args) throws InterruptedException {
		int counts = 1000000;
		int tCounts = 16;
		final Set<Long> set = Collections.synchronizedSet(new HashSet<>(counts));
		CountDownLatch latch = new CountDownLatch(tCounts);
		final IdGenerator idWorker0 = new IdGenerator();
		for (int i = 0; i < tCounts; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					long start = System.currentTimeMillis();
					for (int j = 0; j < counts; j++) {
						long id = idWorker0.nextId();
						if (set.contains(id)) {
							System.out.println("冲突 [" + id + "]");
							break;
						}
						set.add(id);
					}
					System.out.println("耗时：" + (System.currentTimeMillis() - start));
					latch.countDown();
				}
			}).start();
		}
		latch.await();
		System.out.println("set.size：" + set.size());
	}*/

}
