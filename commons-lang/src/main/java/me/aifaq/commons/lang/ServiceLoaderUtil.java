package me.aifaq.commons.lang;

import java.util.*;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 17:11 2017/12/7
 */
public class ServiceLoaderUtil {
    /**
     * Java SPI 获取类实例
     *
     * @param classloader
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T load(ClassLoader classloader, Class<T> clazz) {
        ServiceLoader<T> loader = ServiceLoader.load(clazz, classloader);
        Iterator<T> iterator = loader.iterator();
        List<T> entityList = new ArrayList<>();
        while (iterator.hasNext()) {
            try {
                entityList.add(iterator.next());
            } catch (ServiceConfigurationError ignore) {
            }
        }
        return entityList.isEmpty() ? null : entityList.get(0);
    }
}
