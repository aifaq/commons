package me.aifaq.commons.lang;

import java.io.InputStream;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:58 2017/12/4
 */
public class IOUtil {

    public static InputStream getResourceAsStream(String path) {
        return getResourceAsStream(path, null);
    }

    public static InputStream getResourceAsStream(String path, Class<?> clazz) {
        if (clazz == null) {
            clazz = IOUtil.class;
        }
        InputStream is = clazz.getResourceAsStream(path);
        if (is != null) {
            return is;
        }
        return ClassLoader.getSystemResourceAsStream(path);
    }
}
