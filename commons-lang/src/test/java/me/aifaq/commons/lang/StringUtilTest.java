package me.aifaq.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 19:29 2017/7/31
 */
public class StringUtilTest {
	@Test
	public void testCamelCase() {
		Assert.assertEquals("userName", StringUtil.camelCase("user_name", false));
	}
}
