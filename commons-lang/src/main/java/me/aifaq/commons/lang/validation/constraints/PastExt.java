/**
 * 
 */
package me.aifaq.commons.lang.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.aifaq.commons.lang.validation.constraintvalidators.PastExtValidatorForCalendar;
import me.aifaq.commons.lang.validation.constraintvalidators.PastExtValidatorForDate;
import me.aifaq.commons.lang.validation.constraintvalidators.PastExtValidatorForLong;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a date whose value must be lower or equal to the specified MaxDate.
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
@Constraint(validatedBy = { PastExtValidatorForDate.class, PastExtValidatorForCalendar.class, PastExtValidatorForLong.class })
public @interface PastExt {

    String value() default "";

    /**
     * 是否包含目标值
     */
    boolean inclusive() default true;

    String message() default "{me.aifaq.validation.constraints.PastExt.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link PastExt} annotations on the same element.
     *
     * @see PastExt
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        PastExt[] value();
    }
}
