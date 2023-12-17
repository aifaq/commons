package me.aifaq.commons.spring.converter;

import me.aifaq.commons.lang.DateUtil;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.convert.converter.Converter;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:10 2017/5/17
 */
public class StringToCalendarConverter implements Converter<String, Calendar> {
	@Override
	public Calendar convert(String source) {
		try {
			final Date date = DateUtil.parseDate(source);
			if (date == null) {
				return null;
			}
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		}
		catch (Exception e) {
			throw new TypeMismatchException(source, Calendar.class, e);
		}
	}
}
