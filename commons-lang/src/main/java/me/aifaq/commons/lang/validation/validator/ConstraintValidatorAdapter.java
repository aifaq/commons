package me.aifaq.commons.lang.validation.validator;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 13:35 2017/8/11
 */
public interface ConstraintValidatorAdapter<T> {
	/**
	 * @param value 为整个对象视图，非某个属性
	 * @return
	 */
	boolean isValid(T value);
}
