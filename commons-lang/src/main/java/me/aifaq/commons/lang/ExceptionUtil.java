package me.aifaq.commons.lang;

import org.apache.commons.lang3.exception.ExceptionUtils;

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

    public static String getRootCauseStackTrace(Throwable throwable) {
        final Throwable cause = ExceptionUtils.getRootCause(throwable);

        return ExceptionUtils.getStackTrace(cause == null ? throwable : cause);
    }
}
