package me.aifaq.commons.lang;

import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 14:45 2017/7/7
 */
public class FileUtil {
	/**
	 * 监听filename所在目录下的文件新建、修改、删除
	 *
	 * @param filename
	 * @param handler
	 * @throws IOException
	 */
	public static void watch(final String filename, final Handler handler) throws IOException {
		Preconditions.checkNotNull(filename);
		Preconditions.checkNotNull(handler);

		final File file = new File(filename);
		if (!file.exists()) {
			throw new FileNotFoundException("找不到" + file.getAbsolutePath());
		}
		final String path = file.isFile() ? file.getParent() : file.getAbsolutePath();

		// 监听filename所在目录下的文件新建、修改、删除
		final FileSystem fileSystem = FileSystems.getDefault();
		final WatchService watchService = fileSystem.newWatchService();
		fileSystem.getPath(path).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

		// 启动一个线程监听内容变化，并重新载入配置
		final Thread watchThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						WatchKey watchKey = watchService.take();
						for (WatchEvent event : watchKey.pollEvents()) {
							final WatchEvent.Kind kind = event.kind();
							if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
								handler.fileCreated((Path) event.context());
							}
							else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
								handler.fileModified((Path) event.context());
							}
							else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
								handler.fileDeleted((Path) event.context());
							}
						}
						watchKey.reset();
					}
					catch (InterruptedException e) {
						break;
					}
				}
			}
		});
		watchThread.setDaemon(true);
		watchThread.start();

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					watchService.close();
				}
				catch (IOException ignore) {
				}
			}
		}));
	}

	public static interface Handler {
		/**
		 * 文件被创建
		 */
		void fileCreated(Path file);

		/**
		 * 文件被修改
		 */
		void fileModified(Path file);

		/**
		 * 文件被删除
		 */
		void fileDeleted(Path file);
	}
}
