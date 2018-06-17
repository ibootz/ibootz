package top.bootz.commons.helper;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

import top.bootz.commons.constant.SymbolConstants;

public final class StringHelper {

	public static final String EMPTY = SymbolConstants.EMPTY;

	private StringHelper() {

	}

	public static String joinWith(String separator, String... strings) {
		if (strings == null) {
			throw new IllegalArgumentException("String varargs must not be null");
		}
		final String sanitizedSeparator = separator == null ? StringUtils.EMPTY : separator;
		final StringBuilder result = new StringBuilder();
		final Iterator<String> iterator = Arrays.asList(strings).iterator();
		while (iterator.hasNext()) {
			final String value = iterator.next();
			result.append(value);
			if (iterator.hasNext()) {
				result.append(sanitizedSeparator);
			}
		}
		return result.toString();
	}

}
