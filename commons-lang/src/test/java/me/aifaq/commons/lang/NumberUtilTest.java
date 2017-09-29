package me.aifaq.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 12:32 2017/7/25
 */
public class NumberUtilTest {
	@Test
	public void testTruncate() {
		Assert.assertEquals(NumberUtil.truncate(12345, 1), 12340);
		Assert.assertEquals(NumberUtil.truncate(12345, 2), 12300);
		Assert.assertEquals(NumberUtil.truncate(12345, 3), 12000);
		Assert.assertEquals(NumberUtil.truncate(12345, 4), 10000);
		Assert.assertEquals(NumberUtil.truncate(12345, 5), 0);
		Assert.assertEquals(NumberUtil.truncate(12345, 6), 0);

		Assert.assertEquals(NumberUtil.truncate(-12345, 1), -12340);
		Assert.assertEquals(NumberUtil.truncate(-12345, 2), -12300);
		Assert.assertEquals(NumberUtil.truncate(-12345, 3), -12000);
		Assert.assertEquals(NumberUtil.truncate(-12345, 4), -10000);
		Assert.assertEquals(NumberUtil.truncate(-12345, 5), 0);
		Assert.assertEquals(NumberUtil.truncate(-12345, 6), 0);

		Assert.assertEquals(NumberUtil.truncate(56789, 1), 56780);
		Assert.assertEquals(NumberUtil.truncate(56789, 2), 56700);
		Assert.assertEquals(NumberUtil.truncate(56789, 3), 56000);
		Assert.assertEquals(NumberUtil.truncate(56789, 4), 50000);
		Assert.assertEquals(NumberUtil.truncate(56789, 5), 0);
		Assert.assertEquals(NumberUtil.truncate(56789, 6), 0);

		Assert.assertEquals(NumberUtil.truncate(-56789, 1), -56780);
		Assert.assertEquals(NumberUtil.truncate(-56789, 2), -56700);
		Assert.assertEquals(NumberUtil.truncate(-56789, 3), -56000);
		Assert.assertEquals(NumberUtil.truncate(-56789, 4), -50000);
		Assert.assertEquals(NumberUtil.truncate(-56789, 5), 0);
		Assert.assertEquals(NumberUtil.truncate(-56789, 6), 0);
	}

	@Test
	public void testGteLte() {
		Assert.assertTrue(NumberUtil.gtZero(1));
		Assert.assertTrue(!NumberUtil.gtZero(0));
		Assert.assertTrue(!NumberUtil.gtZero(-1));


		Assert.assertTrue(NumberUtil.geZero(1));
		Assert.assertTrue(NumberUtil.geZero(0));
		Assert.assertTrue(!NumberUtil.geZero(-1));


		Assert.assertTrue(!NumberUtil.ltZero(1));
		Assert.assertTrue(!NumberUtil.ltZero(0));
		Assert.assertTrue(NumberUtil.ltZero(-1));


		Assert.assertTrue(!NumberUtil.leZero(1));
		Assert.assertTrue(NumberUtil.leZero(0));
		Assert.assertTrue(NumberUtil.leZero(-1));
	}
}
