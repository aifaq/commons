package me.aifaq.commons.lang.annotation;

import java.lang.annotation.*;

/**
 * 标明该类或方法不是线程安全的
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 15:48 2017/5/22
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS) // The original version used RUNTIME
public @interface NotThreadSafe {
}
