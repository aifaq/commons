package me.aifaq.commons.lang.ability;

/**
 * 初始化能力
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 17:03 2019/6/24
 */
@FunctionalInterface
public interface Initializable {
    void init() throws Exception;
}
