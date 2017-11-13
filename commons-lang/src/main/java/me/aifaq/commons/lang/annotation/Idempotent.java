package me.aifaq.commons.lang.annotation;

import java.lang.annotation.*;

/**
 * 标明该操作是幂等的
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:15 2017/11/13
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Idempotent {
}
