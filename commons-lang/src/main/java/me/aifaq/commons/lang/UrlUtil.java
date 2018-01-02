package me.aifaq.commons.lang;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.aifaq.commons.lang.StringUtil.FORWARD_SLASH;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:44 2017/11/3
 */
public class UrlUtil {
    private static final Pattern PATTERN = Pattern.compile("(([a-z]+:)?//)?(.*)");

    /**
     * 获取相对uri，包含参数
     * <p>
     * <pre>
     * getRelativePath("http://github.aifaq.me/user/get?id=9527") = "/user/get?id=9527"
     * </pre>
     *
     * @param url
     * @return
     */
    public static String getRelativeUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return StringUtils.trimToEmpty(url);
        }
        final Matcher matcher = PATTERN.matcher(url);
        if (matcher.find()) {
            final String text = matcher.group(3);
            if (matcher.group(1) == null) {
                return text;
            }
            final int index = text.indexOf('/');
            if (index < 0) {
                return text;
            }
            return text.substring(index);
        }
        return url;
    }

    /**
     * 获取相对uri，不包含参数
     * <p>
     * <pre>
     * getRelativePath("http://github.aifaq.me/user/get?id=9527") = "/user/get"
     * </pre>
     *
     * @param url
     * @return
     */
    public static String getRelativePath(String url) {
        url = getRelativeUrl(url);
        final int index = url.indexOf('?');
        if (index < 0) {
            return url;
        }
        return url.substring(0, index);
    }

    /**
     * 拼接url地址
     *
     * @param urls
     * @return
     */
    public static String concat(String... urls) {
        if (ArrayUtils.isEmpty(urls)) {
            return StringUtils.EMPTY;
        }
        if (urls.length == 1) {
            return StringUtils.trimToEmpty(urls[0]);
        }

        String url = StringUtils.trimToEmpty(urls[0]);
        for (int i = 1; i < urls.length; i++) {
            if (StringUtils.isBlank(urls[i])) {
                continue;
            }
            final String uri = StringUtils.trimToEmpty(urls[i]);
            if (url.endsWith(FORWARD_SLASH)) {
                if (uri.startsWith(FORWARD_SLASH)) {
                    url += uri.substring(1);
                } else {
                    url += uri;
                }
            } else {
                if (uri.startsWith(FORWARD_SLASH)) {
                    url += uri;
                } else {
                    url += FORWARD_SLASH + uri;
                }
            }
        }
        return url;
    }
}
