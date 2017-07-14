package me.aifaq.commons.lang;

import me.aifaq.commons.lang.base.FunctionAdapter;
import me.aifaq.commons.lang.base.OperableFunction;
import me.aifaq.commons.lang.base.TypeFunction;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:40 2017/6/16
 */
public class ArrayUtilTest {
	@Test
	public void testNewArrayAndFill() {
		final int length = 10;
		final Integer val = 1;
		final Integer[] integerArray = ArrayUtil.newArrayAndFill(Integer.class, length, val);

		Assert.assertTrue(length == integerArray.length);

		for (int i = 0; i < length; i++) {
			Assert.assertTrue(integerArray[i].equals(val));
		}
	}

	@Test
	public void testTransformList() {
		final int size = 10;
		final Map<String, Integer>[] sources = new Map[size];
		for (int i = 0; i < size; i++) {
			sources[i] = MapUtil.newHashMap("id", i);
		}
		final List<Integer> idList = ArrayUtil
				.transformList(sources, new FunctionAdapter<Map<String, Integer>, Integer>() {
					@Override
					public Integer apply(Map<String, Integer> source) {
						return source.get("id");
					}
				});

		for (int i = 0; i < size; i++) {
			Assert.assertTrue(idList.get(i) == i);
		}
	}

	@Test
	public void testTransform() {
		final int size = 10;
		final Map<String, Integer>[] sources = new Map[size];
		for (int i = 0; i < size; i++) {
			sources[i] = MapUtil.newHashMap("id", i);
		}
		final Integer[] idArray = ArrayUtil
				.transform(sources, new TypeFunction<Map<String, Integer>, Integer>() {
					@Override
					public Integer apply(Map<String, Integer> source) {
						return source.get("id");
					}
				});

		for (int i = 0; i < size; i++) {
			Assert.assertTrue(idArray[i] == i);
		}
	}

	@Test
	public void testSum() {
		final int size = 10;
		final Map<String, Integer>[] sources = new Map[size];
		for (int i = 0; i < size; i++) {
			sources[i] = MapUtil.newHashMap("id", i);
		}

		final BigDecimal sum = ArrayUtil.sum(sources, new OperableFunction<Map<String, Integer>>() {
			@Override
			public BigDecimal apply(Map<String, Integer> source) {
				return new BigDecimal(source.get("id"));
			}
		});

		Assert.assertTrue(sum.equals(BigDecimal.valueOf(45)));
	}
}
