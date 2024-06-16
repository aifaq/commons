package me.aifaq.commons.base;

import junit.framework.TestCase;

import java.util.List;

/**
 * @author eclipse
 * @since 18:11 2024/6/16
 */
public class PageResponseTest extends TestCase {

    public void testBuild() {
        final PageResponse<Object> pageResponse = new PageResponse<>();
        final List<Object> data = pageResponse.getData().getContent();
        System.out.println(data);
    }

}