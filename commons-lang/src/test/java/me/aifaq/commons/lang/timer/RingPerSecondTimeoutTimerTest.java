package me.aifaq.commons.lang.timer;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 17:34 2017/6/26
 */
@Ignore
public class RingPerSecondTimeoutTimerTest {
	RingPerSecondTimeoutTimer timer;

	int size = 100;

	CountDownLatch cdl = new CountDownLatch(size);

	@Before
	public void before() {
		timer = new RingPerSecondTimeoutTimer(10, new RingPerSecondTimeoutTimer.Handler<Map<String, Object>>() {
			@Override
			public void handle(Map<String, Object> object) {
				try {
					final long ctm = System.currentTimeMillis();

					System.out.println(object.get("id") + ":" + (ctm - (Long) object.get("timeout")));
				} finally {
					cdl.countDown();
				}
			}
		}, Executors.newCachedThreadPool());
	}

	@Test
	public void testAdd() throws InterruptedException {
		for (int i = 0; i < size; i++) {
			final Map<String, Object> map = Maps.newHashMap();
			map.put("id", i);
			map.put("timeout", System.currentTimeMillis());

			timer.add(map);

			TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 30) * 200);
		}

		cdl.await();
	}
}
