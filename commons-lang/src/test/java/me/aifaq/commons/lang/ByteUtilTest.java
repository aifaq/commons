package me.aifaq.commons.lang;

import me.aifaq.commons.lang.ByteUtil;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 15:15 2017/6/16
 */
public class ByteUtilTest {

	@Test
	public void testToLogString() {
		final int length = 500;
		final byte[] bytes = new byte[length];
		ThreadLocalRandom.current().nextBytes(bytes);

		System.out.println(ByteUtil.toLogString(bytes));
	}
}
