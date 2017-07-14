package me.aifaq.commons.lang;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

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
}
