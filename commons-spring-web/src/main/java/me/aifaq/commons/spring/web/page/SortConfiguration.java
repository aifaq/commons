package me.aifaq.commons.spring.web.page;

import java.lang.annotation.*;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:22 2017/7/23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface SortConfiguration {
	String[] allowSorts() default {};

	/**
	 * {@link #allowSorts()} 为空是否允许所有
	 */
	boolean emptyAllowAll() default true;

	String[] denySorts() default {};
}
