package me.aifaq.commons.lang.validation;

import jakarta.validation.Configuration;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.junit.Before;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:12 2017/8/2
 */
public class AbstractValidationTest {

	protected Validator validator;

	@Before
	public void before() {
		Configuration<?> configure = Validation.byDefaultProvider().configure();
		configure.addProperty(HibernateValidatorConfiguration.FAIL_FAST, "false");
		Validator validator = configure.buildValidatorFactory().getValidator();
		this.validator = validator;
	}
}
