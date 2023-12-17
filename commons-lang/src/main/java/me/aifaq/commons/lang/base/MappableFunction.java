package me.aifaq.commons.lang.base;

import java.util.Map;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:19 2017/7/14
 */
public abstract class MappableFunction<S, K, V> implements Function<S, Map.Entry<K, V>> {
	@Override
	public boolean skipIfNull() {
		return true;
	}

	/**
	 * apply返回值为null值必须跳过
	 */
	@Override
	final public boolean skipIfApplyNull() {
		return true;
	}
}
