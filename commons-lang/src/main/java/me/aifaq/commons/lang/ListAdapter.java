package me.aifaq.commons.lang;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 20:01 2017/10/11
 */
public abstract class ListAdapter<T> implements List<T> {
    protected abstract List<T> adaptList();

    @Override
    public int size() {
        return adaptList().size();
    }

    @Override
    public boolean isEmpty() {
        return adaptList().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return adaptList().contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return adaptList().iterator();
    }

    @Override
    public Object[] toArray() {
        return adaptList().toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return adaptList().toArray(a);
    }

    @Override
    public boolean add(T t) {
        return adaptList().add(t);
    }

    @Override
    public boolean remove(Object o) {
        return adaptList().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return adaptList().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return adaptList().addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return adaptList().addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return adaptList().removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return adaptList().retainAll(c);
    }

    @Override
    public void clear() {
        adaptList().clear();
    }

    @Override
    public T get(int index) {
        return adaptList().get(index);
    }

    @Override
    public T set(int index, T element) {
        return adaptList().set(index, element);
    }

    @Override
    public void add(int index, T element) {
        adaptList().add(index, element);
    }

    @Override
    public T remove(int index) {
        return adaptList().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return adaptList().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return adaptList().lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return adaptList().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return adaptList().listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return adaptList().subList(fromIndex, toIndex);
    }
}
