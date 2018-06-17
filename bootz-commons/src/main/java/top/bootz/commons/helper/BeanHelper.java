package top.bootz.commons.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BeanHelper {

	private BeanHelper() {
	}

	/**
	 * 类字段值复制
	 * 
	 * @throws BeanCopyException
	 * @throws ReflectiveOperationException
	 */
	public static void copyProperties(final Object source, final Object target) {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		BeanCopier.create(source.getClass(), target.getClass(), false).copy(source, target, null);
	}

	public static void copyProperties(Object source, Object target, String[] properties) {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		BeanWrapper src = new BeanWrapperImpl(source);
		BeanWrapper trg = new BeanWrapperImpl(target);
		Object value;
		for (String propertyName : properties) {
			try {
				value = src.getPropertyValue(propertyName);
				if (value != null && (value instanceof String)) {
					value = ((String) value).trim();
				}
				trg.setPropertyValue(propertyName, value);
			} catch (BeansException e) {
				log.debug("bean copy fail:" + e.getMessage(), e);
			}
		}
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
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
