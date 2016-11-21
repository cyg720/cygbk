package com.cyg.framework.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * web工具类，可以获取当前请求的 request 
 * @author 王道兵
 *
 */
public final class WebContextUtil {
	
	private static ServletRequestAttributes getServletWebRequest() {
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}
	
	/**
	 * SpringMvc下获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return getServletWebRequest().getRequest();

	}
	/**
	 * SpringMvc下获取session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}
}
