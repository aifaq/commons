package me.aifaq.commons.lang.base;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ClassUtils;

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
        Preconditions.checkNotNull(type, "type must not be null");
        
        this.type = ClassUtils.primitiveToWrapper(type);
    }

    public Class<T> getType() {
        return type;
    }
}
