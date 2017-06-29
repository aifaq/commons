package me.aifaq.commons.lang.base;

import java.math.BigDecimal;

/**
 * @author Wang Wei
 * @since 11:38 2017/6/16
 */
public abstract class OperateFunction<S> implements Function<S, BigDecimal> {
	@Override
	final public boolean skipIfNull() {
		return true;
	}

	@Override
	final public boolean skipIfApplyNull() {
		return true;
	}
}
