package me.aifaq.commons.lang;

import me.aifaq.commons.lang.base.DefaultFunction;
import me.aifaq.commons.lang.CollectionUtil;
import me.aifaq.commons.lang.MapUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wang Wei
 * @since 19:50 2017/6/12
 */
public class CollectionUtilTest {
	@Test
	public void testTransformList() {
		final int size = 10;
		final List<Map<String, Integer>> sources = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			sources.add(MapUtil.newHashMap("id", i));
		}
		final List<Integer> idList = CollectionUtil
				.transformList(sources, new DefaultFunction<Map<String, Integer>, Integer>() {
					@Override
					public Integer apply(Map<String, Integer> source) {
						return source.get("id");
					}
				});

		for (int i = 0; i < size; i++) {
			Assert.assertTrue(idList.get(i) == i);
		}
	}
}
