package me.aifaq.commons.lang;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Wang Wei
 * @since 20:16 2017/5/18
 */
public class CookieUtil {
	/**
	 * 根据cookie名称获取cookie
	 *
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		final Cookie[] cookies = request.getCookies();

		if (ArrayUtils.isEmpty(cookies)) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (StringUtils.equals(cookie.getName(), name)) {
				return cookie;
			}
		}

		return null;
	}

	/**
	 * 根据cookie名称获取cookie值
	 *
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		final Cookie cookie = getCookie(request, name);

		return cookie != null ? cookie.getValue() : null;
	}
}
