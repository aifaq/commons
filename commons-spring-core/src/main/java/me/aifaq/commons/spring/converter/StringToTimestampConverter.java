package me.aifaq.commons.spring.converter;

import me.aifaq.commons.lang.DateUtil;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:10 2017/5/17
 */
public class StringToTimestampConverter implements Converter<String, Timestamp> {
	@Override
	public Timestamp convert(String source) {
		try {
			final Date date = DateUtil.parseDate(source);
			return date == null ? null : new Timestamp(date.getTime());
		}
		catch (Exception e) {
			throw new TypeMismatchException(source, Timestamp.class, e);
		}
	}
}
