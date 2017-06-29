/*
 *  Copyright 2011 sunli [sunli1223@gmail.com][weibo.com@sunli1223]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.google.code.fqueue;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.ByteUtil;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * 基于文件系统的持久化队列
 * 
 * @author sunli
 * @date 2010-8-13
 * @version $Id: FQueue.java 2 2011-07-31 12:25:36Z sunli1223@gmail.com $
 */
public class FQueue extends AbstractQueue<byte[]> implements BlockingQueue<byte[]>,
		java.io.Serializable {
	private static final Logger logger = Logger.getLogger(FQueue.class.getName());
	private static final long serialVersionUID = -5960741434564940154L;

	private FSQueue fsQueue;

	private int count;

	/** Main lock guarding all access */
	private final ReentrantLock lock;
	/** Condition for waiting takes */
	private final Condition notEmpty;
	/** Condition for waiting puts */
	private final Condition notFull;

	public FQueue(String path) throws Exception {
		this(path, ByteUtil.ONE_MB * 300);
	}

	public FQueue(String path, long logsize) throws Exception {
		fsQueue = new FSQueue(path, logsize);
		count = fsQueue.getQueueSize();
		lock = new ReentrantLock();
		notEmpty = lock.newCondition();
		notFull = lock.newCondition();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				close();
			}
		});
	}

	private void close() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			fsQueue.close();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Iterator<byte[]> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] peek() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int remainingCapacity() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super byte[]> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super byte[]> c, int maxElements) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			return count;
		} finally {
			lock.unlock();
		}
	}

	private void insert(byte[] x) {
		try {
			fsQueue.add(x);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		++count;
		notEmpty.signal();
	}

	private byte[] extract() {
		byte[] x;
		try {
			x = fsQueue.readNextAndRemove();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		--count;
		notFull.signal();
		return x;
	}

	@Override
	public void put(byte[] e) throws InterruptedException {
		Preconditions.checkNotNull(e);

		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			insert(e);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean offer(byte[] e) {
		Preconditions.checkNotNull(e);

		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			insert(e);
			return true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean offer(byte[] e, long timeout, TimeUnit unit)
			throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] poll() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			return (count == 0) ? null : extract();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public byte[] poll(long timeout, TimeUnit unit) throws InterruptedException {
		long nanos = unit.toNanos(timeout);
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			while (count == 0) {
				if (nanos <= 0)
					return null;
				nanos = notEmpty.awaitNanos(nanos);
			}
			return extract();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public byte[] take() throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			while (count == 0)
				notEmpty.await();
			return extract();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void clear() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			fsQueue.clear();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public String toString() {
		return "FQueue[" + fsQueue.getPath() + "]";
	}
}
