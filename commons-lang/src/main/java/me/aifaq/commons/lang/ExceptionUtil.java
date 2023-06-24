package me.aifaq.commons.lang;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

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

    public static String getRootCauseStackTrace(Throwable t) {
        final Throwable cause = ExceptionUtils.getRootCause(t);

        return ExceptionUtils.getStackTrace(cause == null ? t : cause);
    }

    public static String getStackTraceAsString(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    public static boolean isCausedBy(Throwable t, Class<? extends Throwable>... causes) {
        Throwable cause = t;
        while (cause != null) {
            for (Class<? extends Throwable> c : causes) {
                if (c.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }
}
