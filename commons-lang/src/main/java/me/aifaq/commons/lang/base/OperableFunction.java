package me.aifaq.commons.lang.base;

import java.math.BigDecimal;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:38 2017/6/16
 */
public abstract class OperableFunction<S> implements Function<S, BigDecimal> {
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
