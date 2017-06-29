package me.aifaq.commons.lang.base;

/**
 * @author Wang Wei
 * @since 19:40 2017/6/12
 */
public interface Function<S, T> {
	T apply(S source);

	/**
	 * 入参为null值是否跳过
	 */
	boolean skipIfNull();

	/**
	 * apply返回值为null值是否跳过
	 */
	boolean skipIfApplyNull();
}
