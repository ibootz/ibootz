package top.bootz.commons.helper;

import java.lang.reflect.Array;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;

import top.bootz.commons.constant.SymbolConstants;

public final class ToStringHelper {
	private ToStringHelper() {

	}

	/**
	 * 输出类toString方法格式的字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		Object result = "";
		Class<?> clz = obj.getClass();
		if (ReflectionHelper.isArray(clz)) {
			result = arrayToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
		} else if (ReflectionHelper.isBaseClassOrString(clz)) {
			result = obj;
		} else {
			result = ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
		}
		return result.toString();
	}

	/**
	 * 输出Json风格的字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJSON(Object obj, boolean wrapClassName) {
		if (!wrapClassName) {
			return JsonHelper.toJSON(obj);
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(obj.getClass().getSimpleName(), JsonHelper.toJSON(obj));
		return jsonObj.toString();
	}

	private static String arrayToString(Object obj, ToStringStyle style) {
		StringBuilder sb = new StringBuilder("");
		int length = Array.getLength(obj);
		sb.append(SymbolConstants.LEFT_BRACKETS);
		for (int i = 0; i < length; i++) {
			Object subObj = Array.get(obj, i);
			subObj = subObj == null ? "" : subObj;
			Class<?> clazz = subObj.getClass();
			if (ReflectionHelper.isBaseClassOrString(clazz)) {
				// 基本类型or字符串类型
				sb.append(String.valueOf(subObj));
			} else if (clazz.isArray()) {
				// 数组
				sb.append(arrayToString(subObj, style));
			} else {
				// 其他一般引用类型
				sb.append(ToStringBuilder.reflectionToString(subObj, style));
			}
			if (i < length - 1) {
				sb.append(SymbolConstants.HALF_WIDTH_COMMA).append(SymbolConstants.HALF_WIDTH_BLANK);
			}
		}
		sb.append(SymbolConstants.RIGHT_BRACKETS);
		return sb.toString();
	}
}
