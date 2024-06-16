package me.aifaq.commons.collect;

import com.google.common.collect.Streams;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author eclipse
 * @since 20:07 2024/2/8
 */
public class Streams2 {
    public static <T> Stream<T> stream(T[] array) {
        return array == null ? Stream.empty() : Arrays.stream(array);
    }

    public static <T> Stream<T> stream(List<T> list) {
        return list == null ? Stream.empty() : list.stream();
    }

    public static <T> Stream<T> stream(Set<T> set) {
        return set == null ? Stream.empty() : set.stream();
    }

    public static <T> Stream<T> stream(Collection<T> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return iterable == null ? Stream.empty() : Streams.stream(iterable);
    }

    public static <T> Stream<T> stream(Iterator<T> iterator) {
        return iterator == null ? Stream.empty() : Streams.stream(iterator);
    }

}
