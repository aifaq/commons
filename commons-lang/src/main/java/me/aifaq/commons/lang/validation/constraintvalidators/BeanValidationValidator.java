package me.aifaq.commons.lang.validation.constraintvalidators;

import me.aifaq.commons.lang.validation.constraints.BeanValidation;
import me.aifaq.commons.lang.validation.validator.ConstraintValidatorAdapter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 13:18 2017/8/11
 */
public class BeanValidationValidator implements ConstraintValidator<BeanValidation, Object> {
	ConstraintValidatorAdapter validator;

	@Override
	public void initialize(BeanValidation constraintAnnotation) {
		final Class<?> clazz = constraintAnnotation.value();
		if (!ConstraintValidatorAdapter.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException(String.format("%s非%s的子类", clazz.getSimpleName(), ConstraintValidatorAdapter.class.getSimpleName()));
		}

		try {
			this.validator = (ConstraintValidatorAdapter) clazz.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("无法实例化%s，请检查默认的构造是否能被正常访问或调用", clazz.getSimpleName()), e);
		}
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return this.validator.isValid(value);
	}
}
