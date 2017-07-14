package me.aifaq.commons.lang;

import org.junit.Test;

import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:05 2017/7/7
 */
public class FileUtilTest {
	@Test
	public void testWatch() throws Exception {
		final CountDownLatch cdl = new CountDownLatch(3);

		FileUtil.watch("D:\\apache-tomcat-8.0.43\\bin\\disconf\\download", new FileUtil.Handler() {
			@Override
			public void fileCreated(Path file) {
				System.out.println(file + " created");
				cdl.countDown();
			}

			@Override
			public void fileModified(Path file) {
				System.out.println(file + " modified");
				cdl.countDown();
			}

			@Override
			public void fileDeleted(Path file) {
				System.out.println(file + " deleted");
				cdl.countDown();
			}
		});

		cdl.await(5, TimeUnit.MINUTES);
	}
}
