package me.aifaq.commons.lang;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:42 2017/7/7
 */
public class EncodeUtil {
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    /**
     * UTF-8 编码
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.wrapToRuntimeException(e);
        }
    }

    /**
     * UTF-8 解码
     */
    public static String urlDecode(String part) {

        try {
            return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.wrapToRuntimeException(e);
        }
    }
}
