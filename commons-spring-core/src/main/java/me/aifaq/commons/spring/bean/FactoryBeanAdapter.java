package me.aifaq.commons.spring.bean;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.ability.Disposable;
import me.aifaq.commons.lang.ability.Initializable;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Closeable;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 14:07 2018/4/13
 */
public class FactoryBeanAdapter<T> implements FactoryBean<T>, InitializingBean, DisposableBean {
    private final Class type;

    private final T target;

    public FactoryBeanAdapter(T target) {
        this(target, target.getClass());
    }

    public FactoryBeanAdapter(T target, Class type) {
        Preconditions.checkNotNull(target);

        if (type == null) {
            this.type = target.getClass();
        } else if (!type.isAssignableFrom(target.getClass())) {
            throw new ClassCastException(String.format("%s cannot be cast to %s", target.getClass().getName(), type.getName()));
        } else {
            this.type = type;
        }
        this.target = target;
    }

    @Override
    public void destroy() throws Exception {
        if (target instanceof Disposable) {
            ((Disposable) target).destroy();
        } else if (target instanceof Closeable) {
            ((Closeable) target).close();
        }
    }

    @Override
    public T getObject() throws Exception {
        return target;
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (target instanceof Initializable) {
            ((Initializable) target).init();
        }
    }
}
