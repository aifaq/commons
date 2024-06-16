package me.aifaq.commons.lang;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 14:01 2017/6/16
 */
public class ObjectUtil {

	/**
	 * @see #join(Object, int, char)
	 */
	public static String join(Object object, int joinTimes) {
		return join(object, joinTimes, CharUtil.COMMA);
	}

	/**
	 * 按次拼接字符串
	 * <pre>
	 * StringUtil.join("?", 2, ',')              = "?,?"
	 * StringUtil.join(null, 3, ';')             = ";;"
	 * </pre>
	 *
	 * @param object    拼接对象
	 * @param joinTimes 拼接次数
	 * @param separator 拼接符
	 * @return
	 */
	public static String join(Object object, int joinTimes, char separator) {
		final Object[] objects = new Object[joinTimes];
		Arrays.fill(objects, object);

		return StringUtils.join(objects, separator);
	}

	/**
	 * 以字符串比对两个对象是否相等
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean equalsToString(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		return StringUtils.equals(o1.toString(), o2.toString());
	}

	/**
	 * 以字符串比对两个对象是否相等，忽略大小写
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean equalsToStringIgnoreCase(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		return StringUtils.equalsIgnoreCase(o1.toString(), o2.toString());
	}

	/**
	 * 判断指定对象中是否有null值
	 *
	 * <pre>
	 * ObjectUtil.isAnyNull(null)             = true
	 * ObjectUtil.isAnyNull(null, "foo")      = true
	 * ObjectUtil.isAnyNull(null, null)       = true
	 * ObjectUtil.isAnyNull("", "bar")        = false
	 * ObjectUtil.isAnyNull("bob", "")        = false
	 * ObjectUtil.isAnyNull("  bob  ", null)  = true
	 * ObjectUtil.isAnyNull(" ", "bar")       = false
	 * ObjectUtil.isAnyNull("foo", "bar")     = false
	 * </pre>
	 *
	 * @param objects
	 * @return 有null值则返回true
	 */
	public static boolean isAnyNull(Object ... objects) {
		if (objects == null) {
			return true;
		}
		for (Object object : objects) {
			if (object == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断指定对象是否都不为null
	 *
	 * <pre>
	 * ObjectUtil.isNoneNull(null)             = false
	 * ObjectUtil.isNoneNull(null, "foo")      = false
	 * ObjectUtil.isNoneNull(null, null)       = false
	 * ObjectUtil.isNoneNull("", "bar")        = true
	 * ObjectUtil.isNoneNull("bob", "")        = true
	 * ObjectUtil.isNoneNull("  bob  ", null)  = true
	 * ObjectUtil.isNoneNull(" ", "bar")       = true
	 * ObjectUtil.isNoneNull("foo", "bar")     = true
	 * </pre>
	 *
	 * @param objects
	 * @return
	 */
	public static boolean isNoneNull(Object ... objects) {
		return !isAnyNull(objects);
	}

	/**
	 * 判断两个对象是否相等，如果源对象为null则直接返回true
	 *
	 * <pre>
	 * ObjectUtil.eqOrNull(null, "123")		= true
	 * ObjectUtil.eqOrNull(null, null)		= true
	 * ObjectUtil.eqOrNull("abc", "abc")	= true
	 * ObjectUtil.eqOrNull("abc", "123")	= false
	 * ObjectUtil.eqOrNull("abc", null)		= false
	 * </pre>
	 *
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean eqOrNull(Object source, Object target) {
		if (source == null) {
			return true;
		}

		return Objects.equals(source, target);
	}

	public static <S, T> T defaultIfNull(S s, Function<? super S, ? extends T> mapper, T defaultValue) {
		return s != null ? mapper.apply(s) : defaultValue;
	}
}
