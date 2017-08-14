/**
 * 
 */
package me.aifaq.commons.lang.validation.constraintvalidators;

import me.aifaq.commons.lang.DateUtil;
import me.aifaq.commons.lang.exception.ImpossibleArrivedException;
import me.aifaq.commons.lang.validation.constraints.PastExt;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 * </pre>
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:35:52 AM May 17, 2016
 */
public abstract class PastExtValidatorAbstract<T> implements ConstraintValidator<PastExt, T> {

    protected PastExt constraintAnnotation;

    @Override
    public void initialize(PastExt constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        // null values are valid
        if (value == null) {
            return true;
        }

        final Date targetDate;
        if (StringUtils.isBlank(constraintAnnotation.value())) {
            targetDate = new Date();
        } else {
            try {
                targetDate = DateUtil.parseDate(constraintAnnotation.value());
            } catch (ParseException e) {
                throw new ConstraintDeclarationException(String.format("the declared value[%s] can not be parsed to java.util.Date", constraintAnnotation.value()), e);
            }
        }

        final long targetMillis = targetDate.getTime();

        final long sourceMillis;
        if (value instanceof Long) {
            sourceMillis = (Long) value;
        } else if (value instanceof Date) {
            sourceMillis = ((Date) value).getTime();
        } else if (value instanceof Calendar) {
            sourceMillis = ((Calendar) value).getTimeInMillis();
        } else {
            throw new ImpossibleArrivedException();
        }

        return (constraintAnnotation.inclusive()) ? sourceMillis <= targetMillis : sourceMillis < targetMillis;
    }
}
