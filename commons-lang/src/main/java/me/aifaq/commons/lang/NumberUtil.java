package me.aifaq.commons.lang;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.base.OperableFunction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 14:00 2017/6/16
 */
public class NumberUtil {
	/**
	 * 数字截取
	 *
	 * @param target
	 * @param digits 位数，1-个位，2-十位，3-百位，... ...
	 * @return
	 */
	public static int truncate(int target, int digits) {
		Preconditions.checkArgument(digits > 0, "digits必须大于0");

		return new BigDecimal(target).setScale(-digits, RoundingMode.DOWN).intValue();
	}

	/**
	 * 数字截取
	 *
	 * @param target
	 * @param digits 位数，1-个位，2-十位，3-百位，... ...
	 * @return
	 */
	public static long truncate(long target, int digits) {
		Preconditions.checkArgument(digits > 0, "digits必须大于0");

		return new BigDecimal(target).setScale(-digits, RoundingMode.DOWN).longValue();
	}

	public static Long toLongObject(Number number) {
		return number == null ? null : number.longValue();
	}

	public static Integer toInteger(Number number) {
		return number == null ? null : number.intValue();
	}

	public static Short toShortObject(Number number) {
		return number == null ? null : number.shortValue();
	}

	public static Byte toByteObject(Number number) {
		return number == null ? null : number.byteValue();
	}

	public static Double toDoubleObject(Number number) {
		return number == null ? null : number.doubleValue();
	}

	public static Float toFloatObject(Number number) {
		return number == null ? null : number.floatValue();
	}

	/**
	 * 求和，跳过null值
	 */
	public static BigDecimal sum(BigDecimal ... sources) {
		return ArrayUtil.sum(sources, new OperableFunction<BigDecimal>() {
			@Override
			public BigDecimal apply(BigDecimal source) {
				return source;
			}
		});
	}

	/**
	 * 求和，跳过null值
	 */
	public static BigDecimal sum(Collection<BigDecimal> sourceList) {
		return CollectionUtil.sum(sourceList, new OperableFunction<BigDecimal>() {
			@Override
			public BigDecimal apply(BigDecimal source) {
				return source;
			}
		});
	}
}
