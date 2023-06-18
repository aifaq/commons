package me.aifaq.commons.lang.timer;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.concurrent.NamedThreadFactory;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 * 1. 把过期时间按秒分成若干份，每一份(每一秒)称作一个slot
 * 2. {@link #currentIndex}相当于时钟上的秒针，每秒移动，指向一个slot
 * 3. 每个slot有两个列表，active和backup，当active列表过期后，backup列表将顶替成为active
 * 4. 新加入的项放入{@link #currentIndex}指向的slot的backup列表中，那么它将在一个周期后过期
 * 5. {@link #currentIndex}指向的slot的active列表中所有元素将被过期
 * 6. 过期的元素会回调用钩子{@link Handler#handle(Object)}，该方法会被{@link #executor}异步处理
 * </pre>
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 9:34 2017/6/26
 */
public class RingPerSecondTimeoutTimer<T> {
	class Slot {
		volatile HashSet<T> activeSet = new HashSet<>();
		volatile HashSet<T> backupSet = new HashSet<>();

		synchronized HashSet<T> expireAndReset() {
			HashSet<T> activeSet = this.activeSet;

			this.activeSet = this.backupSet;
			this.backupSet = new HashSet<>();

			return activeSet;
		}

		synchronized boolean add(T e) {
			return backupSet.add(e);
		}

		synchronized boolean remove(T e) {
			// 先从backup列表中找，找不到再从active列表中找
			return backupSet.remove(e) || activeSet.remove(e);
		}
	}

	private final ArrayList<Slot> slotList;

	// 每秒执行
	private final ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor(
			new NamedThreadFactory(this.getClass().getSimpleName()));

	// 当前指针
	private final AtomicLong currentIndex = new AtomicLong(1);

	// 这里会存在并发，所以使用 ConcurrentHashMap
	private final ConcurrentHashMap<T, Integer> indexMap = new ConcurrentHashMap<>();

	private final Handler<T> handler;
	private final Executor executor;

	public RingPerSecondTimeoutTimer(final int timeout, final Handler<T> handler,
			Executor executor) {
		Preconditions.checkArgument(timeout > 0);
		Preconditions.checkNotNull(handler);

		this.slotList = new ArrayList<>(timeout);
		for (int i = 0; i < timeout; i++) {
			this.slotList.add(i, new Slot());
		}

		this.handler = handler;
		this.executor = executor;

		this.timer.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				// 这里currentIndex移动了
				final int index = (int) (currentIndex.getAndIncrement() % slotList.size());
				final Slot slot = slotList.get(index);
				// 当前指向的slot的active列表将过期
				// backup顶替成为active
				// 重置 backup
				final Set<T> expiredSet = slot.expireAndReset();

				// 因为这里是单线程跑，所以不用关心并发
				if (CollectionUtils.isNotEmpty(expiredSet)) {
					for (final T element : expiredSet) {
						// 删除索引
						if (!indexMap.remove(element, index)) {
							// ignore
							continue;
						}
						// 回调钩子，异步执行；同步会造成timer执行阻塞，无法实现每秒调用
						RingPerSecondTimeoutTimer.this.executor.execute(new Runnable() {
							@Override
							public void run() {
								try {
									handler.handle(element);
								}
								catch (Exception ignore) {
								}
							}
						});
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

	/**
	 * 加入过期队列
	 */
	public boolean add(T element) {
		if (element == null) {
			return false;
		}
		// 加入到当前slot的backup列表，将在一个周期后过期
		final int index = getCurrentIndex();
		final boolean result = this.slotList.get(index).add(element);
		if (result) {
			this.indexMap.put(element, index);
		}
		return result;
	}

	/**
	 * 从过期队列中删除项
	 */
	public boolean remove(T element) {
		if (element == null) {
			return false;
		}
		final Integer index = this.indexMap.remove(element);
		if (index != null) {
			return this.slotList.get(index).remove(element);
		}
		return false;
	}

	/**
	 * 延长过期
	 */
	public boolean extend(T element) {
		if (element == null) {
			return false;
		}
		final Integer index = this.indexMap.get(element);
		if (index != null) {
			// 移除老的
			this.slotList.get(index).remove(element);
		}
		return add(element);
	}

	/**
	 * 当不存在时，加入到过期队列
	 */
	public boolean addIfAbsent(T element) {
		if (element == null) {
			return false;
		}
		final int index = getCurrentIndex();

		final Integer oldIndex = this.indexMap.putIfAbsent(element, index);
		if (oldIndex != null) {
			return false;
		}

		return this.slotList.get(index).add(element);
	}

	public static interface Handler<T> {
		void handle(T object);
	}
}
