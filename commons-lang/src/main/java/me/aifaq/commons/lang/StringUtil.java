package me.aifaq.commons.lang;

import me.aifaq.commons.lang.base.TypeFunction;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 15:13 2017/5/22
 */
public class StringUtil {
	public static final String COMMA = Character.valueOf(CharUtil.COMMA).toString();
	public static final String SEMICOLON = Character.valueOf(CharUtil.SEMICOLON).toString();
	public static final String SPACING = Character.valueOf(CharUtil.SPACING).toString();
	public static final String BR = Character.valueOf(CharUtil.BR).toString();

	public static final String NULL = "null";

	/**
	 * @see #concatTwoSide(String, String, String)
	 */
	public static String concatTwoSide(String source, String str) {
		return concatTwoSide(source, str, str);
	}

	/**
	 * 在目标字符串两侧拼接字符串
	 * <pre>
	 * StringUtil.concatTwoSide("卡阿", "杰西", "尔芭") = "杰西卡阿尔芭"
	 * </pre>
	 *
	 * @param source 目标字符串
	 * @param left   拼左边
	 * @param right  拼右边
	 * @return
	 */
	public static String concatTwoSide(String source, String left, String right) {
		final int capacity = source.length() + left.length() + right.length();
		final StringBuilder sb = new StringBuilder(capacity);
		sb.append(left).append(source).append(right);
		return sb.toString();
	}

	/**
	 * 判断字符串是否为空，任何一个为空则返回true
	 * <pre>
	 * StringUtil.isOneBlank(null) = true
	 * StringUtil.isOneBlank("a",null) = true
	 * StringUtil.isOneBlank("a","") = true
	 * StringUtil.isOneBlank("a"," ") = true
	 * StringUtil.isOneBlank("a","b") = false
	 * </pre>
	 *
	 * @param sources
	 * @return
	 */
	public static boolean isOneBlank(String... sources) {
		if (ArrayUtils.isEmpty(sources)) {
			return true;
		}
		for (String source : sources) {
			if (StringUtils.isBlank(source)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断字符串是否相同，只要匹配一个则返回true
	 * <pre>
	 * StringUtil.equalsOne(null, null) = true
	 * StringUtil.equalsOne(null, "a") = false
	 * StringUtil.equalsOne(null, "a", null) = true
	 * StringUtil.equalsOne("b", "a", null) = false
	 * StringUtil.equalsOne("b", "a", null, "b") = true
	 * </pre>
	 *
	 * @param target
	 * @param sources
	 * @return
	 */
	public static boolean equalsOne(String target, String... sources) {
		if (target == null) {
			return sources == null;
		}
		if (ArrayUtils.isEmpty(sources)) {
			return false;
		}
		for (String source : sources) {
			if (StringUtils.equals(target, source)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @see #splitAsList(String, String)
	 */
	public static List<String> splitAsList(String text) {
		return splitAsList(text, COMMA);
	}

	/**
	 * 分隔字符串返回List
	 * <pre>
	 * StringUtils.split(null, *)         = []
	 * StringUtils.split("", *)           = []
	 * StringUtils.split("abc def", null) = ["abc", "def"]
	 * StringUtils.split("abc def", " ")  = ["abc", "def"]
	 * StringUtils.split("abc  def", " ") = ["abc", "def"]
	 * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
	 * </pre>
	 *
	 * @param text
	 * @param separatorChars
	 * @return
	 */
	public static List<String> splitAsList(String text, String separatorChars) {
		final String[] array = StringUtils.split(text, separatorChars);
		if (ArrayUtils.isEmpty(array)) {
			return new ArrayList<>(0);
		}
		return Arrays.asList(array);
	}

	/**
	 * @see #splitAsList(String, String, TypeFunction)
	 */
	public static <T> List<T> splitAsList(String text, TypeFunction<String, T> function) {
		return splitAsList(text, COMMA, function);
	}

	/**
	 * 分隔字符串返回List
	 *
	 * @param text
	 * @param separatorChars
	 * @param function
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> splitAsList(String text, String separatorChars,
			TypeFunction<String, T> function) {
		final String[] array = StringUtils.split(text, separatorChars);
		if (ArrayUtils.isEmpty(array)) {
			return new ArrayList<>(0);
		}

		return ArrayUtil.transformList(array, function);
	}

	/**
	 * @see #camelCase(String, boolean)
	 */
	public static String camelCase(String inputString) {
		return camelCase(inputString, false);
	}

	/**
	 * Gets the camel case string.
	 *
	 * @param inputString
	 *            the input string
	 * @param firstCharacterUppercase
	 *            the first character uppercase
	 * @return the camel case string
	 */
	public static String camelCase(String inputString, boolean firstCharacterUppercase) {
		StringBuilder sb = new StringBuilder();

		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			switch (c) {
			case '_':
			case '-':
			case '@':
			case '$':
			case '#':
			case ' ':
			case '/':
			case '&':
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
				break;

			default:
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}

		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}

		return sb.toString();
	}
}
