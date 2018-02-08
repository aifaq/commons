package me.aifaq.commons.lang;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:44 2018/2/8
 */
public class ClassUtil {
    /**
     * 简化类目
     * <p>
     * <pre>
     *   ClassUtil.simplifyClassName("com.netease.kaola.sc.util.ClassUtil") = "c.n.k.s.u.ClassUtil"
     * </pre>
     *
     * @param className
     * @return
     */
    public static String simplifyClassName(String className) {
        if (StringUtils.isBlank(className)) {
            return className;
        }

        final String[] parts = StringUtils.split(className, ".");

        if (parts.length <= 1) {
            return className;
        }
        final StringBuilder result = new StringBuilder(className.length());

        for (int i = 0; i < parts.length - 1; i++) {
            result.append(parts[i].charAt(0)).append('.');
        }

        return result.append(parts[parts.length - 1]).toString();
    }
}
