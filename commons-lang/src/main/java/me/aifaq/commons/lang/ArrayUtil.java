package me.aifaq.commons.lang;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.base.Function;
import me.aifaq.commons.lang.base.OperateFunction;
import me.aifaq.commons.lang.base.TypeFunction;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Wang Wei
 * @since 15:14 2017/5/22
 */
public class ArrayUtil {

	/**
	 * 按指定类型以及长度创建数组
	 *
	 * @param componentType
	 * @param length
	 * @param <T>
	 * @return
	 */
	public static <T> T[] newArray(Class<?> componentType, int length) {
		return (T[]) Array.newInstance(componentType, length);
	}

	/**
	 * 按指定类型以及长度创建数组并填充
	 *
	 * @param componentType
	 * @param length
	 * @param val
	 * @param <T>
	 * @return
	 */
	public static <T> T[] newArrayAndFill(Class<?> componentType, int length, Object val) {
		final T[] array = (T[]) Array.newInstance(componentType, length);

		Arrays.fill(array, val);

		return array;
	}

	/**
	 * 自定义函数转换得到数组
	 *
	 * @param sources  源数据集合
	 * @param function 转换函数
	 * @param <S>      源数据类型
	 * @param <T>      目标数据类型
	 * @return
	 */
	public static <S, T> T[] transform(S[] sources, TypeFunction<S, T> function) {
		Preconditions.checkNotNull(function);

		if (ArrayUtils.isEmpty(sources)) {
			return newArray((Class) function.getType(), 0);
		}
		final List<T> targets = new ArrayList<>(sources.length);
		for (S source : sources) {
			if (source == null && function.skipIfNull()) {
				continue;
			}
			final T target = function.apply(source);
			if (target == null && function.skipIfApplyNull()) {
				continue;
			}
			targets.add(target);
		}
		final T[] array = newArray((Class) function.getType(), targets.size());
		return targets.toArray(array);
	}

	/**
	 * 自定义函数转换得到List
	 *
	 * @param sources  源数据集合
	 * @param function 转换函数
	 * @param <S>      源数据类型
	 * @param <T>      目标数据类型
	 * @return
	 */
	public static <S, T> List<T> transformList(S[] sources, Function<S, T> function) {
		Preconditions.checkNotNull(function);

		if (ArrayUtils.isEmpty(sources)) {
			return new ArrayList<>(0);
		}
		final List<T> targets = new ArrayList<>(sources.length);
		for (S source : sources) {
			if (source == null && function.skipIfNull()) {
				continue;
			}
			final T target = function.apply(source);
			if (target == null && function.skipIfApplyNull()) {
				continue;
			}
			targets.add(target);
		}
		return targets;
	}

	/**
	 * 自定义函数转换得到Set
	 *
	 * @param sources  源数据集合
	 * @param function 转换函数
	 * @param <S>      源数据类型
	 * @param <T>      目标数据类型
	 * @return
	 */
	public static <S, T> Set<T> transformSet(S[] sources, Function<S, T> function) {
		Preconditions.checkNotNull(function);

		if (ArrayUtils.isEmpty(sources)) {
			return new HashSet<>();
		}
		final Set<T> targets = new HashSet<>();
		for (S source : sources) {
			if (source == null && function.skipIfNull()) {
				continue;
			}
			final T target = function.apply(source);
			if (target == null && function.skipIfApplyNull()) {
				continue;
			}
			targets.add(target);
		}
		return targets;
	}

	/**
	 * 求和
	 *
	 * @param sources  源数据集合
	 * @param function 转换函数
	 * @param <S>      源数据类型
	 * @return
	 */
	public static <S> BigDecimal sum(S[] sources, OperateFunction<S> function) {
		Preconditions.checkNotNull(function);

		if (ArrayUtils.isEmpty(sources)) {
			return BigDecimal.ZERO;
		}
		BigDecimal result = BigDecimal.ZERO;
		for (S source : sources) {
			if (source == null) {
				continue;
			}
			final BigDecimal target = function.apply(source);
			if (target == null) {
				continue;
			}
			result = result.add(target);
		}
		return result;
	}

	/**
	 * 是否存在null值
	 *
	 * @param array
	 * @return
	 */
	public static boolean hasNull(Object[] array) {
		if (array == null) {
			return true;
		}
		for (Object object : array) {
			if (object == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 拼接字符串
	 *
	 * @param array      目标数组
	 * @param separator  拼接字符
	 * @param ignoreNull 是否忽略null值
	 * @return
	 */
	public static String join(Object[] array, Object separator, boolean ignoreNull) {
		if (ArrayUtils.isEmpty(array)) {
			return StringUtils.EMPTY;
		}
		final StringBuilder result = new StringBuilder();
		int mark = 0;
		for (Object object : array) {
			if (object == null && ignoreNull) {
				continue;
			}
			if (mark++ > 0) {
				result.append(separator);
			}
			result.append(object);
		}
		return result.toString();
	}

	/**
	 * @see #join(Object[], Object, boolean)
	 */
	public static String join(Object[] array, Object seperator) {
		return join(array, seperator, true);
	}

	/**
	 * @see #join(Object[], Object, boolean)
	 */
	public static String join(Object[] array) {
		return join(array, StringUtil.COMMA, true);
	}

	/**
	 * 拼接字符串
	 *
	 * @param array        目标数组
	 * @param separator    拼接字符
	 * @param nullReplaced null被替换的值
	 * @return
	 */
	public static String join(Object[] array, Object separator, Object nullReplaced) {
		if (ArrayUtils.isEmpty(array)) {
			return StringUtils.EMPTY;
		}
		final StringBuilder result = new StringBuilder();
		int mark = 0;
		for (Object object : array) {
			if (mark++ > 0) {
				result.append(separator);
			}
			result.append(object == null ? nullReplaced : object);
		}
		return result.toString();
	}
}
