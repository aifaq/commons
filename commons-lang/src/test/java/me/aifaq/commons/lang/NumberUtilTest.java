package me.aifaq.commons.lang;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

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

    @Test
    public void testToString() {
        long l;
        String text;
        for (int i = 0; i < 1000; i++) {
            l = ThreadLocalRandom.current().nextLong();
            Assert.assertTrue(NumberUtil.parse62Long(NumberUtil.to62String(l)) == l);

            l = ThreadLocalRandom.current().nextLong();
            text = NumberUtil.toString(l, 32);
            Assert.assertTrue(text.equals(Long.toString(l, 32)));
            Assert.assertTrue(NumberUtil.parseLong(text, 32) == l);
            Assert.assertTrue(Long.parseLong(text, 32) == l);
        }
    }
}
