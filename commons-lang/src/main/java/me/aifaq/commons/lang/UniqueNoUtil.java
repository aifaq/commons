package me.aifaq.commons.lang;

import java.util.Calendar;

/**
 * 获取唯一序列号，生成规则：时间戳（精确到毫秒）+ 最后一段的IP地址 + 序列号
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:12 2017/5/17
 */
public class UniqueNoUtil {
	private static final Calendar cal = Calendar.getInstance();
	private static final int ROTATION = 999;
	private static final int ipMix = ipMix();

	private volatile static int seq = 0;

	static int ipMix() {
		String[] ipAddresses = IpUtil.LOCAL_IP.split("\\.");
		return Integer.parseInt(ipAddresses[3]);
	}

	public static String next(String prefix) {
		return prefix + next().substring(2);
	}

	static synchronized String next() {
		if (seq > ROTATION) {
			seq = 0;
		}
		cal.setTimeInMillis(System.currentTimeMillis());
		return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL%2$03d%3$03d", cal, ipMix, seq++);
	}
}
