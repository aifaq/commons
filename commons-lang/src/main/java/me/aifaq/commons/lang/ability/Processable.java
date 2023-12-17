package me.aifaq.commons.lang.ability;

/**
 * 处理能力
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 18:00 2019/6/24
 */
@FunctionalInterface
public interface Processable<T> {
    void process(T args);
}
