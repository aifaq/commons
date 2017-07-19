package me.aifaq.commons.lang;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wang Wei [5waynewang@gmail.com]
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

	/**
	 * 创建 {@link java.util.Map.Entry} 对象
	 * @param k
	 * @param v
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Map.Entry<K, V> newMapEntry(K k, V v) {
		return new DefaultMapEntry(k, v);
	}

	static class DefaultMapEntry<K, V> implements Map.Entry<K, V> {

		private final K key;
		private V value;

		public DefaultMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			return this.value = value;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			DefaultMapEntry<?, ?> that = (DefaultMapEntry<?, ?>) o;

			if (key != null ? !key.equals(that.key) : that.key != null)
				return false;
			return value != null ? value.equals(that.value) : that.value == null;
		}

		@Override
		public int hashCode() {
			int result = key != null ? key.hashCode() : 0;
			result = 31 * result + (value != null ? value.hashCode() : 0);
			return result;
		}
	}

	public static <K, V> V get(Map<K, V> map, K key) {
		return (V) MapUtils.getObject(map, key);
	}

	public static <K, V> V get(Map<K, V> map, K key, V defaultValue) {
		return (V) MapUtils.getObject(map, key, defaultValue);
	}
}
