package me.aifaq.commons.lang;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:01 2017/5/18
 */
public class DateUtilTest {
    @Test
    public void testParseDate() throws ParseException {
        String text;

        text = "2017";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMdd"), "20170101");

        text = "201701";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMdd"), "20170101");

        text = "20170102";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMdd"), "20170102");

        text = "2017010203";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHH"), "2017010203");

        text = "201701020304";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmm"), "201701020304");

        text = "20170102030405";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmmss"), "20170102030405");

        text = "201701020304056";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmmssS"), "201701020304056");

        text = "2017010203040567";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmmssSS"), "2017010203040567");

        text = "20170102030405678";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmmssSSS"), "20170102030405678");

        text = "2017-01";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMdd"), "20170101");

        text = "2017.01";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMdd"), "20170101");

        text = "2017-01-02";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMdd"), "20170102");

        text = "2017.01.02";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMdd"), "20170102");

        text = "2017-01-02 03";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHH"), "2017010203");

        text = "2017-01-02 03:04";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmm"), "201701020304");

        text = "2017-01-02 03:04:05";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmmss"), "20170102030405");

        text = "2017-01-02 03:04:05.6";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmmssS"), "201701020304056");

        text = "2017-01-02 03:04:05.67";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmmssSS"), "2017010203040567");

        text = "2017-01-02 03:04:05.678";
        Assert.assertEquals(DateFormatUtils.format(DateUtil.parseDate(text), "yyyyMMddHHmmssSSS"), "20170102030405678");

        text = String.valueOf(System.currentTimeMillis());
        Assert.assertEquals(DateUtil.parseDate(text), new Date(Long.parseLong(text)));
    }

    @Test
    public void testMin() {

        final Date min = new Date(System.currentTimeMillis() - 10000L);

        Assert.assertEquals(min, DateUtil.minIgnoreNull(min, null, new Date(System.currentTimeMillis() - 1000L), new Date(System.currentTimeMillis() - 100L)));
        Assert.assertEquals(null, DateUtil.minPriorNull(min, null, new Date(System.currentTimeMillis() - 1000L), new Date(System.currentTimeMillis() - 100L)));
    }

    @Test
    public void testMax() {

        final Date max = new Date(System.currentTimeMillis() + 10000L);

        Assert.assertEquals(max, DateUtil.maxIgnoreNull(max, null, new Date(System.currentTimeMillis() + 1000L), new Date(System.currentTimeMillis() + 100L)));
        Assert.assertEquals(null, DateUtil.maxPriorNull(max, null, new Date(System.currentTimeMillis() + 1000L), new Date(System.currentTimeMillis() + 100L)));
    }
}
