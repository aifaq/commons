/**
 * 
 */
package me.aifaq.commons.lang.validation.constraintvalidators;

import me.aifaq.commons.lang.DateUtil;
import me.aifaq.commons.lang.validation.constraints.PastExt;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.util.Date;

/**
 * <pre>
 * </pre>
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:35:52 AM May 17, 2016
 */
public class PastExtValidatorForDate implements ConstraintValidator<PastExt, Date> {

    PastExt constraintAnnotation;

    @Override
    public void initialize(PastExt constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        // null values are valid
        if (value == null) {
            return true;
        }

        if (StringUtils.isBlank(constraintAnnotation.value())) {
            return value.getTime() <= System.currentTimeMillis();
        } else {
            try {
                return value.getTime() <= DateUtil.parseDate(constraintAnnotation.value()).getTime();
            } catch (ParseException e) {
                throw new ConstraintDeclarationException(String.format("the declared value[%s] can not be parsed to java.util.Date", constraintAnnotation.value()), e);
            }
        }
    }

}
