/**
 * 
 */
package me.aifaq.commons.lang.validation.constraintvalidators;

import me.aifaq.commons.lang.DateUtil;
import me.aifaq.commons.lang.validation.constraints.FutureExt;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.util.Calendar;

/**
 * <pre>
 * </pre>
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:35:52 AM May 17, 2016
 */
public class FutureExtValidatorForCalendar implements ConstraintValidator<FutureExt, Calendar> {

    FutureExt constraintAnnotation;

    @Override
    public void initialize(FutureExt constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Calendar value, ConstraintValidatorContext context) {
        // null values are valid
        if (value == null) {
            return true;
        }

        if (StringUtils.isBlank(constraintAnnotation.value())) {
            return value.getTimeInMillis() > System.currentTimeMillis();
        } else {
            try {
                return value.getTimeInMillis() > DateUtil.parseDate(constraintAnnotation.value()).getTime();
            } catch (ParseException e) {
                throw new ConstraintDeclarationException(String.format("the declared value[%s] can not be parsed to java.util.Date", constraintAnnotation.value()), e);
            }
        }
    }

}
