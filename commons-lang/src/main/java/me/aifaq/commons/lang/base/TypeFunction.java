package me.aifaq.commons.lang.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:14 2017/6/16
 */
public abstract class TypeFunction<S, T> extends FunctionAdapter<S, T> {
    protected final Class type;

    public TypeFunction() {
        Type superClass = getClass().getGenericSuperclass();

        this.type = (Class) ((ParameterizedType) superClass).getActualTypeArguments()[1];
    }

    public TypeFunction(Class type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }
}
