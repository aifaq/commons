/**
 * 
 */
package me.aifaq.commons.lang.validation.constraints;

import me.aifaq.commons.lang.validation.constraintvalidators.FutureExtValidatorForCalendar;
import me.aifaq.commons.lang.validation.constraintvalidators.FutureExtValidatorForDate;
import me.aifaq.commons.lang.validation.constraintvalidators.FutureExtValidatorForLong;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Calendar;

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
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { FutureExtValidatorForDate.class, FutureExtValidatorForCalendar.class, FutureExtValidatorForLong.class })
public @interface FutureExt {

    String value() default "";

    /**
     * 当以当前日期衡量Future，当天的时间是否都有效
     * <pre>
     *     eg: 当前 2017-08-14 15:24:35
     *     那么是否只要是 2017-08-14 当天的时间，都会有效
     * </pre>
     */
    boolean includeAllDayIfFutureNow() default false;

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
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        FutureExt[] value();
    }
}
