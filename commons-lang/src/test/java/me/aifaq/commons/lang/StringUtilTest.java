package me.aifaq.commons.lang;

import me.aifaq.commons.lang.base.TypeFunction;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 19:29 2017/7/31
 */
public class StringUtilTest {
    @Test
    public void testCamelCase() {
        Assert.assertEquals("userName", StringUtil.camelCase("user_name", false));
    }

    @Test
    public void testSplit() {
        final Integer[] results = StringUtil.split("0,1,2,3,4,5", new TypeFunction<String, Integer>() {
            @Override
            public Integer apply(String source) {
                return Integer.valueOf(source);
            }
        });

        for (int i = 0; i < results.length; i++) {
            Assert.assertTrue(i == results[i]);
        }
    }

    @Test
    public void testSplitAsSet() {
        final Set<String> strSet = StringUtil.splitAsSet("1,2,3,1,2");
        Assert.assertEquals(strSet.size(), 3);


        final Set<Long> longSet = StringUtil.splitAsSet("1,2,3,1,2", new TypeFunction<String, Long>() {
            @Override
            public Long apply(String source) {
                return Long.valueOf(source);
            }
        });
        Assert.assertEquals(longSet.size(), 3);
    }
}
