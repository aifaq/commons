package me.aifaq.commons.lang;

import me.aifaq.commons.lang.IpUtil;
import org.junit.Test;

/**
 * @author Wang Wei
 * @since 14:19 2017/5/18
 */
public class IpUtilTest {
	@Test
	public void testGetLocalIp() {
		System.out.println(IpUtil.LOCAL_IP);
	}
}
