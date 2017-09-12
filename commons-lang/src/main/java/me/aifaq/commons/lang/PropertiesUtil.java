package me.aifaq.commons.lang;

import com.google.common.base.Charsets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:20 2017/7/7
 */
public class PropertiesUtil {

	public static Properties loadProperties(String file) throws IOException {
		try (FileInputStream input = new FileInputStream(file)) {
			return loadProperties(input);
		}
	}

	public static Properties loadProperties(InputStream input) throws IOException {
		final Properties properties = new Properties();
		properties.load(new InputStreamReader(input, Charsets.UTF_8));
		return properties;
	}

	/**
	 * 获取动态的properties；
	 * 实时同步最新的内容，保证最终一致；
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Properties loadDynamicProperties(final String file) throws IOException {
		final DynamicProperties properties = new DynamicProperties();

		FileUtil.watch(file, new FileUtil.Handler() {
			@Override
			public void fileCreated(Path path) {
				override();
			}

			@Override
			public void fileModified(Path path) {
				override();
			}

			void override() {
				try {
					properties.setProperties(loadProperties(file));
				} catch (IOException e) {
				}
			}

			@Override
			public void fileDeleted(Path path) {
				properties.setProperties(null);
			}
		});
		return properties;
	}

	private static class DynamicProperties extends Properties {
		synchronized void setProperties(Properties properties) {
			this.defaults = properties;
		}
	}
}
