package me.aifaq.commons.lang;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wang Wei
 * @since 10:45 2017/5/26
 */
public class MapUtil {
	public static <K, V> Map<K, V> newHashMap(K key, V value) {
		final Map<K, V> map = Maps.newHashMap();
		map.put(key, value);
		return map;
	}

	public static <K, V> Map<K, V> newLinkedHashMap(K key, V value) {
		final Map<K, V> map = Maps.newLinkedHashMap();
		map.put(key, value);
		return map;
	}

	/**
	 * 根据key获取value，
	 * 如果value非null，则直接返回value,
	 * 如果value为null，则调用{@link Callable#call()}生成value，再通过{@link Map#put(Object, Object)}把新value放到map中并返回
	 *
	 * @param map
	 * @param key
	 * @param callable
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> V getIfNullPut(Map<K, V> map, K key, Callable<V> callable) {
		Preconditions.checkNotNull(map);

		V value = map.get(key);
		if (value != null) {
			return value;
		}

		try {
			value = callable.call();
		}
		catch (Exception e) {
			throw ExceptionUtil.wrapToRuntimeException(e);
		}
		if (value != null) {
			map.put(key, value);
		}

		return value;
	}

	/**
	 * 根据key获取value，
	 * 如果value非null，则直接返回value,
	 * 如果value为null，则调用{@link Callable#call()}生成value，再通过{@link ConcurrentHashMap#putIfAbsent(Object, Object)}把新value放到map中并返回
	 *
	 * @param map
	 * @param key
	 * @param callable
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> V getIfNullPutIfAbsent(ConcurrentHashMap<K, V> map, K key,
			Callable<V> callable) {
		Preconditions.checkNotNull(map);

		V value = map.get(key);
		if (value != null) {
			return value;
		}

		try {
			value = callable.call();
		}
		catch (Exception e) {
			throw ExceptionUtil.wrapToRuntimeException(e);
		}
		if (value != null) {
			V oldValue = map.putIfAbsent(key, value);
			if (oldValue != null) {
				return oldValue;
			}
		}

		return value;
	}
}
