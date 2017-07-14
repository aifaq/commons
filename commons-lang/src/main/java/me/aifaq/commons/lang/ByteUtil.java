package me.aifaq.commons.lang;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 14:18 2017/5/18
 */
public class ByteUtil {
	public static final long ONE_KB = 1024L;
	public static final long ONE_MB = 1024 * ONE_KB;
	public static final long ONE_GB = 1024 * ONE_MB;
	public static final long ONE_TB = 1024 * ONE_GB;
	public static final long ONE_PB = 1024 * ONE_TB;

	/**
	 * 二进制转十六进制字符
	 *
	 * @param b
	 * @return
	 */
	public static String toHexString(byte b) {
		return Integer.toHexString(b & 0xff).toUpperCase();
	}

	/**
	 * int转成4个byte
	 *
	 * @param i
	 * @return
	 */
	public static byte[] toByteArray(int i) {
		final byte[] b = new byte[4];
		b[0] = (byte) (0xff & i);
		b[1] = (byte) ((0xff00 & i) >> 8);
		b[2] = (byte) ((0xff0000 & i) >> 16);
		b[3] = (byte) ((0xff000000 & i) >> 24);
		return b;
	}

	/**
	 * 4个byte转int
	 *
	 * @param bytes
	 * @return
	 */
	public static int toInt(byte[] bytes) {
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}

	/**
	 * long转成8个byte
	 *
	 * @param l
	 * @return
	 */
	public static byte[] toByteArray(long l) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			b[i] = (byte) (l >>> (56 - (i * 8)));
		}
		return b;
	}

	/**
	 * 8个byte转long
	 *
	 * @param bytes
	 * @return
	 */
	public static long toLong(byte[] bytes) {
		long temp = 0;
		long res = 0;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = bytes[i] & 0xff;
			res |= temp;
		}
		return res;
	}

	/**
	 * @see #toLogString(byte[], int)
	 */
	public static String toLogString(byte[] bytes) {
		return toLogString(bytes, 50);
	}

	/**
	 * byte数组转成日志可读的字符串，方便排查问题
	 *
	 * @param bytes
	 * @param rowSize
	 * @return
	 */
	public static String toLogString(byte[] bytes, int rowSize) {
		if (bytes == null) {
			return StringUtil.NULL;
		}
		if (bytes.length == 0) {
			return "[]";
		}
		final int size = 6;
		final int capacity = (bytes.length * size) + (bytes.length / rowSize);
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			result.append(StringUtils.rightPad(String.valueOf(bytes[i]), size, CharUtil.SPACING));
			if (((i + 1) % rowSize) == 0) {
				result.append(CharUtil.BR);
			}
		}
		return result.toString();
	}
}
