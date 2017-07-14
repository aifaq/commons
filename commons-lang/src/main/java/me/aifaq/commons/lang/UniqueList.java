package me.aifaq.commons.lang;

import java.util.ArrayList;
import java.util.Collection;

/**
 * copy from {@link com.mongodb.util.UniqueList}
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 15:32 2017/6/16
 */
public class UniqueList<T> extends ArrayList<T> {

	private static final long serialVersionUID = -5142543069290180113L;

	@Override
	public boolean add(T t) {
		if (contains(t))
			return false;
		return super.add(t);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean added = false;
		for (T t : c)
			added = add(t) || added;
		return added;
	}
}
