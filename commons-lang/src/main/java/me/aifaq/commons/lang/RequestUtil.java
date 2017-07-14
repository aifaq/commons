package me.aifaq.commons.lang;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 20:20 2017/5/18
 */
public class RequestUtil {
	public static final String UNKNOWN = "unknown";

	/**
	 * 获取请求主机的IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotBlank(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
			return ip;
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotBlank(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
			return ip;
		}
		ip = request.getHeader("Proxy-Client-IP");
		if (StringUtils.isNotBlank(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
			return ip;
		}
		ip = request.getHeader("WL-Proxy-Client-IP");
		if (StringUtils.isNotBlank(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
			return ip;
		}
		ip = request.getHeader("HTTP_CLIENT_IP");
		if (StringUtils.isNotBlank(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
			return ip;
		}
		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (StringUtils.isNotBlank(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
