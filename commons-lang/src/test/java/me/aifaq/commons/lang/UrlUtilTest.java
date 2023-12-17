package me.aifaq.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:47 2018/1/2
 */
public class UrlUtilTest {

    @Test
    public void testConcat() {
        Assert.assertEquals("/a/b/c", UrlUtil.concat("/a", "b", "c"));
        Assert.assertEquals("/a/b/c", UrlUtil.concat("/a/", "b", "/c"));
        Assert.assertEquals("/a/b/c", UrlUtil.concat(" /a/", "b ", " /c "));
    }
}
