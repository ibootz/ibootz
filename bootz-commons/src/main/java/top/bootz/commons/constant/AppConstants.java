package top.bootz.commons.constant;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

public class AppConstants {

	public static final class AppLocale {

		public static final Locale DEFAULT_LOCALE = AppConstants.AppLocale.CHINA;

		public static final Locale CHINA = Locale.CHINA;

		public static final Locale US = Locale.US;

		public static final Locale UK = Locale.UK;

		public static final Locale JAPAN = Locale.JAPAN;

		public static final Locale TAIWAN = Locale.TAIWAN;

		private AppLocale() {
		}

		public static Locale getDefault(HttpServletRequest request) {
			Locale locale = RequestContextUtils.getLocale(request);
			if (locale == null) {
				return DEFAULT_LOCALE;
			}
			return locale;
		}
	}

	private AppConstants() {
	}

}
