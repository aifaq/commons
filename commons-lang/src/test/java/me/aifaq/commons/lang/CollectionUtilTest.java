package me.aifaq.commons.lang;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.aifaq.commons.lang.base.FunctionAdapter;
import me.aifaq.commons.lang.base.MappableFunction;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Wang Wei [5waynewang@gmail.com]
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
    public void testTransformMap() {
        final int size = 10;
        final List<Integer> sources = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sources.add(i);
        }
        final Map<Integer, String> idMap = CollectionUtil
                .transformMap(sources, new MappableFunction<Integer, Integer, String>() {
                    @Override
                    public Map.Entry<Integer, String> apply(Integer source) {
                        return MapUtil.newMapEntry(source, source + "name");
                    }
                });

        for (int i = 0; i < size; i++) {
            Assert.assertTrue(idMap.get(i).equals(i + "name"));
        }
    }

    @Test
    public void testFirst() {
        Assert.assertTrue(CollectionUtil.first(Lists.newArrayList(Arrays.asList("a", "b", "c"))).equals("a"));
        Assert.assertTrue(CollectionUtil.first(Lists.newLinkedList(Arrays.asList("a", "b", "c"))).equals("a"));
        Assert.assertTrue(CollectionUtil.first(Sets.newHashSet(Arrays.asList("a", "b", "c"))).equals("a"));
    }

    @Test
    public void testLast() {
        Assert.assertTrue(CollectionUtil.last(Lists.newArrayList(Arrays.asList("a", "b", "c"))).equals("c"));
        Assert.assertTrue(CollectionUtil.last(Lists.newLinkedList(Arrays.asList("a", "b", "c"))).equals("c"));
        Assert.assertTrue(CollectionUtil.last(Sets.newHashSet(Arrays.asList("a", "b", "c"))).equals("c"));
    }
}
