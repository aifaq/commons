/**
 *
 */
package me.aifaq.commons.lang.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.aifaq.commons.lang.validation.constraintvalidators.FutureExtValidatorForCalendar;
import me.aifaq.commons.lang.validation.constraintvalidators.FutureExtValidatorForDate;
import me.aifaq.commons.lang.validation.constraintvalidators.FutureExtValidatorForLong;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a date whose value must be higher or equal to the specified MinDate.
 * <p/>
 * Supported types are:
 * <ul>
 * <li>{@code java.util.Date}</li>
 * <li>{@code java.util.Calendar}</li>
 * <li>{@code java.sql.Date}</li>
 * <li>{@code java.sql.Timestamp}</li>
 * <li>{@code java.lang.Long}</li>
 * </ul>
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:25:08 AM May 17, 2016
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FutureExtValidatorForDate.class, FutureExtValidatorForCalendar.class, FutureExtValidatorForLong.class})
public @interface FutureExt {

    /**
     * 默认当前时间
     */
    String value() default "";

    /**
     * 是否包含目标值
     */
    boolean inclusive() default true;

    String message() default "{me.aifaq.validation.constraints.FutureExt.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link FutureExt} annotations on the same element.
     *
     * @see FutureExt
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        FutureExt[] value();
    }
}
