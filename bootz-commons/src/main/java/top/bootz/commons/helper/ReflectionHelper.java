package top.bootz.commons.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectionHelper {

	private ReflectionHelper() {
	}

	/**
	 * 判断当前类是否是基本类型或者基本类型的包装类
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseClass(Class<?> clazz) {
		return isInteger(clazz) || isShort(clazz) || isByte(clazz) || isLong(clazz) || isDouble(clazz) || isFloat(clazz)
				|| isBoolean(clazz) || isCharacter(clazz);
	}

	public static boolean isBaseClassOrString(Class<?> clazz) {
		return isBaseClass(clazz) || isString(clazz);
	}

	public static boolean isInteger(Class<?> clazz) {
		return clazz == Integer.class || clazz == int.class;
	}

	public static boolean isShort(Class<?> clazz) {
		return clazz == Short.class || clazz == short.class;
	}

	public static boolean isByte(Class<?> clazz) {
		return clazz == Byte.class || clazz == byte.class;
	}

	public static boolean isLong(Class<?> clazz) {
		return clazz == Long.class || clazz == long.class;
	}

	public static boolean isDouble(Class<?> clazz) {
		return clazz == Double.class || clazz == double.class;
	}

	public static boolean isFloat(Class<?> clazz) {
		return clazz == Float.class || clazz == float.class;
	}

	public static boolean isBoolean(Class<?> clazz) {
		return clazz == Boolean.class || clazz == boolean.class;
	}

	public static boolean isCharacter(Class<?> clazz) {
		return clazz == Character.class || clazz == char.class;
	}

	public static boolean isString(Class<?> clazz) {
		return clazz == String.class;
	}

	public static boolean isDate(Class<?> clazz) {
		return clazz == java.util.Date.class;
	}

	public static boolean isTimestamp(Class<?> clazz) {
		return clazz == java.sql.Timestamp.class;
	}

	public static boolean isArray(Class<?> clazz) {
		return clazz.isArray();
	}

	public static boolean isCollection(Class<?> clazz) {
		return isList(clazz) || isSet(clazz);
	}

	public static boolean isList(Class<?> clazz) {
		return List.class.isAssignableFrom(clazz);
	}

	public static boolean isSet(Class<?> clazz) {
		return Set.class.isAssignableFrom(clazz);
	}

	public static boolean isMap(Class<?> clazz) {
		return Map.class.isAssignableFrom(clazz);
	}

}
