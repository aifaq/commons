package me.aifaq.commons.lang.base;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 19:45 2017/6/12
 */
public abstract class FunctionAdapter<S, T> implements Function<S, T> {
	@Override
	public boolean skipIfNull() {
		return true;
	}

	@Override
	public boolean skipIfApplyNull() {
		return true;
	}
}
