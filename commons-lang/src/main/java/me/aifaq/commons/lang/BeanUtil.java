package me.aifaq.commons.lang;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 19:53 2017/7/31
 */
public class BeanUtil {
	private static ConcurrentHashMap<Class, ConcurrentHashMap<String/*fieldName*/, Pair<Field, Method>>> FIELD_METHOD_MAP = new ConcurrentHashMap<>();

	public static <T> T copyResultSetToBean(T dest, ResultSet rs) throws SQLException {
		return copyResultSetToBean(dest, rs, null);
	}

	/**
	 * 将数据集游标所在位置的行数据复制到以驼峰风格对应的JavaBean对象中
	 *
	 * @param dest 目标JavaBean对象
	 * @param rs 行数据，调用前需要将游标移到需要复制的行数据处
	 * @param columnFieldMap column与field的映射关系，优先
	 * @return
	 * @throws
	 */
	public static <T> T copyResultSetToBean(T dest, ResultSet rs, Map<String/*columnName*/, String/*fieldName*/> columnFieldMap) throws SQLException {
		if (dest == null || rs == null) {
			return dest;
		}
		ConcurrentHashMap<String/*fieldName*/, Pair<Field, Method>> fieldMethodMap = FIELD_METHOD_MAP.get(dest.getClass());
		if (fieldMethodMap == null) {
			// cache
			final ConcurrentHashMap<String/*fieldName*/, Pair<Field, Method>> old = FIELD_METHOD_MAP
					.putIfAbsent(dest.getClass(), fieldMethodMap = new ConcurrentHashMap<>());
			if (old != null) {
				fieldMethodMap = old;
			}
		}

		final ResultSetMetaData rsmd = rs.getMetaData();
		final int colNum = rsmd.getColumnCount();
		for (int i = 1; i <= colNum; i++) {
			final String columnName = rsmd.getColumnLabel(i);

			String fieldName = MapUtil.get(columnFieldMap, columnName);
			if (StringUtils.isBlank(fieldName)) {
				fieldName = StringUtil.camelCase(columnName);
			}

			Pair<Field, Method> pair = fieldMethodMap.get(fieldName);
			if (pair == null) {
				Field field;
				try {
					field = dest.getClass().getDeclaredField(fieldName);
				} catch (NoSuchFieldException ignore) {
					field = null;
				}
				pair = MutablePair.of(field, null);

				if (field == null) {
					// cache
					fieldMethodMap.putIfAbsent(fieldName, pair);
					continue;
				}
			} else if (pair.getLeft() == null || pair.getRight() == null) {
				continue;
			}

			final Class<?> propertyType = pair.getLeft().getType();

			final Object propertyValue;
			if (propertyType == String.class) {
				propertyValue = rs.getString(i);
			}
			else if (propertyType == Date.class || propertyType == java.sql.Date.class) {
				propertyValue = rs.getDate(i);
			}
			else if (propertyType == Timestamp.class) {
				propertyValue = rs.getTimestamp(i);
			}
			else if (propertyType == Time.class) {
				propertyValue = rs.getTime(i);
			}
			else if (propertyType == Byte.class || propertyType == byte.class) {
				propertyValue = rs.getByte(i);
			}
			else if (propertyType == Short.class || propertyType == short.class) {
				propertyValue = rs.getShort(i);
			}
			else if (propertyType == Integer.class || propertyType == int.class) {
				propertyValue = rs.getInt(i);
			}
			else if (propertyType == Long.class || propertyType == long.class) {
				propertyValue = rs.getLong(i);
			}
			else if (propertyType == Float.class || propertyType == float.class) {
				propertyValue = rs.getFloat(i);
			}
			else if (propertyType == Double.class || propertyType == double.class) {
				propertyValue = rs.getDouble(i);
			}
			else if (propertyType == Boolean.class || propertyType == boolean.class) {
				propertyValue = rs.getBoolean(i);
			}
			else if (propertyType == BigDecimal.class) {
				propertyValue = rs.getBigDecimal(i);
			}
			else if (propertyType == URL.class) {
				propertyValue = rs.getURL(i);
			}
			else if (propertyType == Blob.class) {
				propertyValue = rs.getBlob(i);
			}
			else if (propertyType == Clob.class) {
				propertyValue = rs.getClob(i);
			}
			else {
				propertyValue = rs.getObject(i);
			}

			final String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

			if (pair.getRight() == null) {
				try {
					pair.setValue(dest.getClass().getDeclaredMethod(setMethodName, propertyType));
				} catch (NoSuchMethodException ignore) {
					pair.setValue(null);
					continue;
				}
			}

			try {
				pair.getRight().invoke(dest, propertyValue);
			} catch (IllegalAccessException | InvocationTargetException ignore) {
				pair.setValue(null);//设置成null，下次直接跳过？
			}
		}

		return dest;
	}
}
