package top.bootz.commons.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cglib.beans.BeanCopier;

public final class BeanHelper {

	private final static Logger LOGGER = LogManager.getLogger(BeanHelper.class);

	private BeanHelper() {

	}

	/**
	 * 类字段值复制
	 * 
	 * @throws BeanCopyException
	 * @throws ReflectiveOperationException
	 */
	public static void copyProperties(final Object source, final Object dest) {
		BeanCopier.create(source.getClass(), dest.getClass(), false).copy(source, dest, null);
	}

	/**
	 * 深度复制，实参类必须实现Serializable接口
	 * 
	 * @param source
	 * @return
	 */
	public static Object deepClone(Object source) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);) {
			oos.writeObject(source);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
