package me.aifaq.commons.lang.ability;

/**
 * 转换能力
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 17:58 2019/6/24
 */
@FunctionalInterface
public interface Transferable<S, T> {

    T transfer(S source);
}
