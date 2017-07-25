package me.aifaq.commons.lang;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
}
