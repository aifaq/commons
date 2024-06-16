package me.aifaq.commons.lang;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 14:25 2018/1/19
 */
public class DaoUtil {
    private static final String[] DUPLICATE_KEY_EXCEPTIONS = {
            "org.springframework.dao.DuplicateKeyException",
            "com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException"};

    /**
     * 是否唯一键重复异常
     *
     * @param e
     * @return
     */
    public static boolean isDuplicateKeyException(RuntimeException e) {
        if (ArrayUtils.contains(DUPLICATE_KEY_EXCEPTIONS, e.getClass().getName())) {
            return true;
        }
        final Throwable cause = ExceptionUtils.getRootCause(e);
        if (cause != null && ArrayUtils.contains(DUPLICATE_KEY_EXCEPTIONS, cause.getClass().getName())) {
            return true;
        }
        return false;
    }

    /**
     * 是否有影响的行
     *
     * @param affectedRows
     * @return
     */
    public static boolean affectedRows(Number affectedRows) {
        return affectedRows != null && affectedRows.longValue() > 0;
    }

    public static String likeTwoSide(String str) {
        return StringUtil.appendTwoSide(str, "%", "%");
    }

    public static String likePrefix(String str) {
        return StringUtil.appendTwoSide(str, "%", "");
    }

    public static String likeSuffix(String str) {
        return StringUtil.appendTwoSide(str, "", "");
    }
}
