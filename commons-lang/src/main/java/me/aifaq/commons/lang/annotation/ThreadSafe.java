package me.aifaq.commons.lang.annotation;

import java.lang.annotation.*;

/**
 * @author Wang Wei
 * @since 15:45 2017/5/22
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS) // The original version used RUNTIME
public @interface ThreadSafe {
}
