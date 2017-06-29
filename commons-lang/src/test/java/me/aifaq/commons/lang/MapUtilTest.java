package me.aifaq.commons.lang;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Wang Wei
 * @since 20:35 2017/6/23
 */
public class MapUtilTest {
	@Test
	public void testGetIfNullPut() {
		final Map<String, Object> map = new HashMap<>();

		final String key = "key";
		final Object value = new Object();

		final Object value1 = MapUtil.getIfNullPut(map, key, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return value;
			}
		});

		Assert.assertEquals(value, value1);

		final Object value2 = MapUtil.getIfNullPut(map, key, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return new Object();
			}
		});

		Assert.assertEquals(value, value2);
	}
}
