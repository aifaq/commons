package me.aifaq.commons.spring.converter;

import me.aifaq.commons.lang.DateUtil;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:10 2017/5/17
 */
public class StringToDateConverter implements Converter<String, Date> {
	@Override
	public Date convert(String source) {
		try {
			return DateUtil.parseDate(source);
		}
		catch (Exception e) {
			throw new TypeMismatchException(source, Date.class, e);
		}
	}
}
