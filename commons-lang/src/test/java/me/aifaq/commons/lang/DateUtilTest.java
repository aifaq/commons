package me.aifaq.commons.lang;

import me.aifaq.commons.lang.DateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		Assert.assertEquals(DateUtil.parseDate(text), new SimpleDateFormat("yyyy").parse(text));

		text = "201701";
		Assert.assertEquals(DateUtil.parseDate(text), new SimpleDateFormat("yyyyMM").parse(text));

		text = "20170102";
		Assert.assertEquals(DateUtil.parseDate(text), new SimpleDateFormat("yyyyMMdd").parse(text));

		text = "2017010203";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyyMMddHH").parse(text));

		text = "201701020304";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyyMMddHHmm").parse(text));

		text = "20170102030405";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyyMMddHHmmss").parse(text));

		text = "201701020304056";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyyMMddHHmmssS").parse(text));

		text = "2017010203040567";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyyMMddHHmmssSS").parse(text));

		text = "20170102030405678";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyyMMddHHmmssSSS").parse(text));

		text = "2017-01";
		Assert.assertEquals(DateUtil.parseDate(text), new SimpleDateFormat("yyyy-MM").parse(text));

		text = "2017-01-02";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyy-MM-dd").parse(text));

		text = "2017-01-02 03";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyy-MM-dd HH").parse(text));

		text = "2017-01-02 03:04";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(text));

		text = "2017-01-02 03:04:05";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(text));

		text = "2017-01-02 03:04:05.6";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(text));

		text = "2017-01-02 03:04:05.67";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").parse(text));

		text = "2017-01-02 03:04:05.678";
		Assert.assertEquals(DateUtil.parseDate(text),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(text));

		text = String.valueOf(System.currentTimeMillis());
		Assert.assertEquals(DateUtil.parseDate(text), new Date(Long.parseLong(text)));
	}
}
