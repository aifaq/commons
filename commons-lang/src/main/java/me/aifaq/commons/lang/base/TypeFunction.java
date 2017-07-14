package me.aifaq.commons.lang.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:14 2017/6/16
 */
public abstract class TypeFunction<S, T> extends FunctionAdapter<S, T> {
	protected final Type type;

	public TypeFunction() {
		Type superClass = getClass().getGenericSuperclass();

		type = ((ParameterizedType) superClass).getActualTypeArguments()[1];
	}

	public Type getType() {
		return type;
	}
}
