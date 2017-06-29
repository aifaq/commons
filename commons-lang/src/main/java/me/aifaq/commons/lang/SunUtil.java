package me.aifaq.commons.lang;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 封装sun包的一些调用
 * @author Wang Wei
 * @since 11:00 2017/5/30
 */
public class SunUtil {
	public static void cleanPrivileged(final Object target, final String methodName) {
		if (target == null || StringUtils.isBlank(methodName)) {
			return;
		}
		AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				try {
					Method getCleanerMethod = target.getClass().getMethod(methodName, new Class[0]);
					getCleanerMethod.setAccessible(true);
					sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(target, new Object[0]);
					cleaner.clean();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
}
