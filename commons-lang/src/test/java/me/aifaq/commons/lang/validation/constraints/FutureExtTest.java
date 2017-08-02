package me.aifaq.commons.lang.validation.constraints;

import me.aifaq.commons.lang.DateUtil;
import me.aifaq.commons.lang.validation.AbstractValidationTest;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:14 2017/8/2
 */
public class FutureExtTest extends AbstractValidationTest {
	@Test
	public void testLong() throws ParseException {
		Long invalidValue;
		Set<ConstraintViolation<TestBean>> set;
		TestBean testBean;

		testBean = new TestBean();
		testBean.setDefaultLong(invalidValue = (System.currentTimeMillis() - 1000));

		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 1);
		Assert.assertEquals(set.iterator().next().getInvalidValue(), invalidValue);

		testBean.setDefaultLong(invalidValue = (System.currentTimeMillis() + 1000));
		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 0);

		testBean = new TestBean();
		testBean.setCustomLong(invalidValue = DateUtil.parseDate("20170731").getTime());

		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 1);
		Assert.assertEquals(set.iterator().next().getInvalidValue(), invalidValue);

		testBean.setCustomLong(invalidValue = DateUtil.parseDate("20170802").getTime());
		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 0);
	}

	@Test
	public void testDate() throws ParseException {
		Date invalidValue;
		Set<ConstraintViolation<TestBean>> set;
		TestBean testBean;

		testBean = new TestBean();
		testBean.setDefaultDate(invalidValue = new Timestamp(System.currentTimeMillis() - 1000));

		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 1);
		Assert.assertEquals(set.iterator().next().getInvalidValue(), invalidValue);

		testBean.setDefaultDate(invalidValue = new Timestamp(System.currentTimeMillis() + 1000));
		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 0);

		testBean = new TestBean();
		testBean.setCustomDate(invalidValue = DateUtil.parseDate("20170731"));

		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 1);
		Assert.assertEquals(set.iterator().next().getInvalidValue(), invalidValue);

		testBean.setCustomDate(invalidValue = DateUtil.parseDate("20170802"));
		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 0);
	}

	@Test
	public void testCalendar() throws ParseException {
		Set<ConstraintViolation<TestBean>> set;
		TestBean testBean;

		testBean = new TestBean();
		testBean.setDefaultCalendar(Calendar.getInstance());
		testBean.getDefaultCalendar().setTimeInMillis(System.currentTimeMillis() - 1000);

		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 1);
		Assert.assertEquals(set.iterator().next().getInvalidValue(), testBean.getDefaultCalendar());

		testBean.getDefaultCalendar().setTimeInMillis(System.currentTimeMillis() + 1000);
		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 0);

		testBean = new TestBean();
		testBean.setCustomCalendar(Calendar.getInstance());
		testBean.getCustomCalendar().setTimeInMillis(DateUtil.parseDate("20170731").getTime());

		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 1);
		Assert.assertEquals(set.iterator().next().getInvalidValue(), testBean.getCustomCalendar());

		testBean.getCustomCalendar().setTimeInMillis(DateUtil.parseDate("20170802").getTime());
		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 0);
	}

	static class TestBean {
		@FutureExt
		private Long defaultLong;
		@FutureExt(value = "20170801")
		private Long customLong;
		@FutureExt
		private Date defaultDate;
		@FutureExt(value = "20170801")
		private Date customDate;
		@FutureExt
		private Calendar defaultCalendar;
		@FutureExt(value = "20170801")
		private Calendar customCalendar;

		public Long getDefaultLong() {
			return defaultLong;
		}

		public void setDefaultLong(Long defaultLong) {
			this.defaultLong = defaultLong;
		}

		public Long getCustomLong() {
			return customLong;
		}

		public void setCustomLong(Long customLong) {
			this.customLong = customLong;
		}

		public Date getDefaultDate() {
			return defaultDate;
		}

		public void setDefaultDate(Date defaultDate) {
			this.defaultDate = defaultDate;
		}

		public Date getCustomDate() {
			return customDate;
		}

		public void setCustomDate(Date customDate) {
			this.customDate = customDate;
		}

		public Calendar getDefaultCalendar() {
			return defaultCalendar;
		}

		public void setDefaultCalendar(Calendar defaultCalendar) {
			this.defaultCalendar = defaultCalendar;
		}

		public Calendar getCustomCalendar() {
			return customCalendar;
		}

		public void setCustomCalendar(Calendar customCalendar) {
			this.customCalendar = customCalendar;
		}
	}
}
