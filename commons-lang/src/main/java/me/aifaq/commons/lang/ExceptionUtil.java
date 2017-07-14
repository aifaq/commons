package me.aifaq.commons.lang;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 12:34 2017/5/30
 */
public class ExceptionUtil {
	public static RuntimeException wrapToRuntimeException(Exception e) {
		return new RuntimeException(e);
	}

	public static RuntimeException wrapToRuntimeException(Exception e, String message) {
		return new RuntimeException(message, e);
	}
}
