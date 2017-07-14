package me.aifaq.commons.lang.timer;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.concurrent.NamedThreadFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 * 1. 把过期时间按秒分成若干份，每一份(每一秒)称作一个slot
 * 2. {@link #currentIndex}相当于时钟上的秒针，每秒移动，指向一个slot
 * 3. 新加入的项放入{@link #currentIndex}指向的上个slot
 * 4. {@link #currentIndex}指向的slot中的所有元素将被过期
 * 5. 过期的元素会回调用钩子{@link Handler#handle(Object)}，该方法会被{@link #executor}异步处理
 * </pre>
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 9:34 2017/6/26
 */
public class RingPerSecondTimeoutTimer<T> {
	private static final Object PRESENT = new Object();

	private final ArrayList<Set<T>> slotList;

	private final ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor(
			new NamedThreadFactory(this.getClass().getSimpleName()));

	private final AtomicLong currentIndex = new AtomicLong(1);

	private final ConcurrentHashMap<T, Integer> indexMap = new ConcurrentHashMap<>();

	private final Handler<T> handler;
	private final Executor executor;

	public RingPerSecondTimeoutTimer(final int timeout, final Handler<T> handler,
			Executor executor) {
		Preconditions.checkArgument(timeout > 0);
		Preconditions.checkNotNull(handler);

		this.slotList = new ArrayList<>(timeout);
		for (int i = 0; i < timeout; i++) {
			this.slotList.add(i, new HashSet<T>());
		}

		this.handler = handler;
		this.executor = executor;

		this.timer.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				final int index = (int) (currentIndex.getAndIncrement() % slotList.size());
				final Set<T> slot = slotList.get(index);
				synchronized (slot) {
					if (CollectionUtils.isNotEmpty(slot)) {
						final Iterator<T> iterator = slot.iterator();
						do {
							final T element = iterator.next();
							iterator.remove();
							// 删除索引
							indexMap.remove(element);
							// 回调钩子
							RingPerSecondTimeoutTimer.this.executor.execute(new Runnable() {
								@Override
								public void run() {
									try {
										handler.handle(element);
									} catch (Exception ignore) {
									}
								}
							});
						}
						while (iterator.hasNext());
					}
				}
			}
		}, 1, 1, TimeUnit.SECONDS);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				timer.shutdown();
			}
		}));
	}

	public int getCurrentIndex() {
		return (int) (this.currentIndex.get() % this.slotList.size());
	}

	public int getNextIndex() {
		return (int) ((this.currentIndex.get() + 1) % this.slotList.size());
	}

	public int getPreviousIndex() {
		return (int) ((this.currentIndex.get() - 1) % this.slotList.size());
	}

	/**
	 * 加入过期队列
	 */
	public void add(T element) {
		// 加入上一个slot
		final int index = getPreviousIndex();
		final Set<T> slot = this.slotList.get(index);
		synchronized (slot) {
			slot.add(element);
			this.indexMap.put(element, index);
		}
	}

	/**
	 * 从过期队列中删除项
	 */
	public void remove(T element) {
		final Integer index = this.indexMap.get(element);
		if (index != null) {
			final Set<T> slot = this.slotList.get(index);
			synchronized (slot) {
				slot.remove(element);
				this.indexMap.remove(element);
			}
		}
	}

	/**
	 * 延长过期
	 */
	public void extend(T element) {
		remove(element);
		add(element);
	}

	public static interface Handler<T> {
		void handle(T object);
	}
}
