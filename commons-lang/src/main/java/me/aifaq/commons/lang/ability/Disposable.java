package me.aifaq.commons.lang.ability;

/**
 * 销毁能力
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 17:04 2019/6/24
 */
@FunctionalInterface
public interface Disposable {
    void destroy() throws Exception;
}
