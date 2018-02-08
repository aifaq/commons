package me.aifaq.commons.lang;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.base.Function;
import me.aifaq.commons.lang.base.MappableFunction;
import me.aifaq.commons.lang.base.OperableFunction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:10 2017/5/17
 */
public class CollectionUtil {
    /**
     * 如果对象为null，返回0
     *
     * @param object
     * @return
     */
    public static int size(Object object) {
        if (object == null) {
            return 0;
        }
        return CollectionUtils.size(object);
    }

    /**
     * 自定义函数转换得到List
     *
     * @param sources  源数据集合
     * @param function 转换函数
     * @param <S>      源数据类型
     * @param <T>      目标数据类型
     * @return
     */
    public static <S, T> List<T> transformList(Collection<S> sources, Function<S, T> function) {
        Preconditions.checkNotNull(function);

        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<>(0);
        }
        final List<T> targets = new ArrayList<>(sources.size());
        for (S source : sources) {
            if (source == null && function.skipIfNull()) {
                continue;
            }
            final T target = function.apply(source);
            if (target == null && function.skipIfApplyNull()) {
                continue;
            }
            targets.add(target);
        }
        return targets;
    }

    /**
     * 自定义函数转换得到Set
     *
     * @param sources  源数据集合
     * @param function 转换函数
     * @param <S>      源数据类型
     * @param <T>      目标数据类型
     * @return
     */
    public static <S, T> Set<T> transformSet(Collection<S> sources, Function<S, T> function) {
        Preconditions.checkNotNull(function);

        if (CollectionUtils.isEmpty(sources)) {
            return new HashSet<>();
        }
        final Set<T> targets = new HashSet<>();
        for (S source : sources) {
            if (source == null && function.skipIfNull()) {
                continue;
            }
            final T target = function.apply(source);
            if (target == null && function.skipIfApplyNull()) {
                continue;
            }
            targets.add(target);
        }
        return targets;
    }

    /**
     * 自定义函数转换得到Map
     *
     * @param sources  源数据集合
     * @param function 转换函数
     * @param <S>      源数据类型
     * @param <K,      V>      目标数据类型
     * @return
     */
    public static <S, K, V> Map<K, V> transformMap(Collection<S> sources, MappableFunction<S, K, V> function) {
        Preconditions.checkNotNull(function);

        if (CollectionUtils.isEmpty(sources)) {
            return new HashMap<>();
        }
        final Map<K, V> targets = new HashMap<>();
        for (S source : sources) {
            if (source == null && function.skipIfNull()) {
                continue;
            }
            final Map.Entry<K, V> entry = function.apply(source);
            if (entry == null && function.skipIfApplyNull()) {
                continue;
            }
            targets.put(entry.getKey(), entry.getValue());
        }
        return targets;
    }

    /**
     * 求和
     *
     * @param sources  源数据集合
     * @param function 转换函数
     * @param <S>      源数据类型
     * @return
     */
    public static <S> BigDecimal sum(Collection<S> sources, OperableFunction<S> function) {
        Preconditions.checkNotNull(function);

        if (CollectionUtils.isEmpty(sources)) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = BigDecimal.ZERO;
        for (S source : sources) {
            if (source == null && function.skipIfNull()) {
                continue;
            }
            final BigDecimal target = function.apply(source);
            if (target == null && function.skipIfApplyNull()) {
                continue;
            }
            result = result.add(target);
        }
        return result;
    }

    /**
     * @see #sum(Collection, OperableFunction)
     */
    public static BigDecimal sum(Collection<BigDecimal> sources) {
        return sum(sources, new OperableFunction<BigDecimal>() {
            @Override
            public BigDecimal apply(BigDecimal source) {
                return source;
            }
        });
    }


    /**
     * 是否存在null值
     *
     * @param collection
     * @return
     */
    public static boolean hasNull(Collection<?> collection) {
        if (collection == null) {
            return true;
        }
        for (Object object : collection) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接字符串
     *
     * @param collection 目标集合
     * @param separator  拼接字符
     * @param ignoreNull 是否忽略null值
     * @return
     */
    public static String join(Collection<?> collection, Object separator, boolean ignoreNull) {
        if (CollectionUtils.isEmpty(collection)) {
            return StringUtils.EMPTY;
        }
        final StringBuilder result = new StringBuilder();
        int mark = 0;
        for (Object object : collection) {
            if (object == null && ignoreNull) {
                continue;
            }
            if (mark++ > 0) {
                result.append(separator);
            }
            result.append(object);
        }
        return result.toString();
    }

    /**
     * @see #join(Collection, Object, boolean)
     */
    public static String join(Collection<?> collection, Object seperator) {
        return join(collection, seperator, true);
    }

    /**
     * @see #join(Collection, Object, boolean)
     */
    public static String join(Collection<?> collection) {
        return join(collection, StringUtil.COMMA, true);
    }

    /**
     * 拼接字符串
     *
     * @param collection   目标集合
     * @param separator    拼接字符
     * @param nullReplaced null被替换的值
     * @return
     */
    public static String join(Collection<?> collection, Object separator, Object nullReplaced) {
        if (CollectionUtils.isEmpty(collection)) {
            return StringUtils.EMPTY;
        }
        final StringBuilder result = new StringBuilder();
        int mark = 0;
        for (Object object : collection) {
            if (mark++ > 0) {
                result.append(separator);
            }
            result.append(object == null ? nullReplaced : object);
        }
        return result.toString();
    }

    /**
     * 添加元素至集合中
     *
     * @param target
     * @param source
     * @param <T>
     * @return
     */
    public static <T> Collection<T> addAll(Collection<T> target, Iterable<T> source) {
        Preconditions.checkNotNull(target);

        if (source == null) {
            return target;
        }
        final Iterator<T> iterator = source.iterator();
        while (iterator.hasNext()) {
            target.add(iterator.next());
        }
        return target;
    }

    /**
     * 获取集合的首个元素
     *
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> T first(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return null;
        }
        final Iterator<T> iterator = collection.iterator();

        return iterator.hasNext() ? iterator.next() : null;
    }

    /**
     * 创建{@link ArrayList}对象，长度为0
     *
     * @param <T>
     * @return
     */
    public static <T> List<T> newEmptyArrayList() {
        return new ArrayList<>(0);
    }
}
