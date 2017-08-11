package me.aifaq.commons.lang.validation.constraints;

import me.aifaq.commons.lang.validation.AbstractValidationTest;
import me.aifaq.commons.lang.validation.validator.ConstraintValidatorAdapter;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 13:57 2017/8/11
 */
public class BeanValidationTest extends AbstractValidationTest {

	@Test
	public void testDepend() {
		Set<ConstraintViolation<BeanValidationTest.TestBean>> set;
		BeanValidationTest.TestBean testBean;

		testBean = new BeanValidationTest.TestBean();

		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 1);
		Assert.assertEquals(set.iterator().next().getMessageTemplate(), "a与b必须至少给一个");

		testBean.setA(1);
		testBean.setB(5);
		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 1);
		Assert.assertEquals(set.iterator().next().getMessageTemplate(), "a等于1的时候b不能等于5");

		testBean.setB(4);
		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 1);
		Assert.assertEquals(set.iterator().next().getMessageTemplate(), "b必须是奇数");

		testBean.setB(3);
		set = this.validator.validate(testBean);
		Assert.assertTrue(set.size() == 0);
	}

	public static class ConstraintValidatorAdapter1
			implements ConstraintValidatorAdapter<TestBean> {
		@Override
		public boolean isValid(TestBean value) {
			if (value.getA() == null && value.getB() == null) {
				return false;
			}
			return true;
		}
	}

	public static class ConstraintValidatorAdapter2
			implements ConstraintValidatorAdapter<TestBean> {
		@Override
		public boolean isValid(TestBean value) {
			if (value.getA() == null) {
				return true;
			}
			else if (value.getA() == 1) {
				if (value.getB() == null || value.getB() == 5) {
					return false;
				}
			}
			return true;
		}
	}

	public static class ConstraintValidatorAdapter3
			implements ConstraintValidatorAdapter<TestBean> {
		@Override
		public boolean isValid(TestBean value) {
			if (value.getB() == null) {
				return true;
			}
			else if (value.getB() % 2 == 0) {
				return false;
			}
			return true;
		}
	}

	@BeanValidation.List({ @BeanValidation(value = ConstraintValidatorAdapter1.class, message = "a与b必须至少给一个"),
			@BeanValidation(value = ConstraintValidatorAdapter2.class, message = "a等于1的时候b不能等于5"),
			@BeanValidation(value = ConstraintValidatorAdapter3.class, message = "b必须是奇数") })
	public static class TestBean {
		private Integer a;
		private Integer b;

		public Integer getA() {
			return a;
		}

		public void setA(Integer a) {
			this.a = a;
		}

		public Integer getB() {
			return b;
		}

		public void setB(Integer b) {
			this.b = b;
		}
	}
}
