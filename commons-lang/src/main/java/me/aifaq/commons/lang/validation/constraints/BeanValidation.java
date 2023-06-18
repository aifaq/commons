package me.aifaq.commons.lang.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.aifaq.commons.lang.validation.constraintvalidators.BeanValidationValidator;
import me.aifaq.commons.lang.validation.validator.ConstraintValidatorAdapter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 通用注解，自定义校验逻辑
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 13:29 2017/8/11
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { BeanValidationValidator.class })
public @interface BeanValidation {
	/**
	 * 自定义校验逻辑实现类
	 * <pre>
	 * 1.必须是 {@link ConstraintValidatorAdapter} 的子类
	 * 2.必须有默认构造
	 * </pre>
	 */
	Class<?> value();

	String message() default "{me.aifaq.validation.constraints.BeanValidation.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * Defines several {@link BeanValidation} annotations on the same element.
	 *
	 * @see BeanValidation
	 */
	@Target({ TYPE, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		BeanValidation[] value();
	}
}
