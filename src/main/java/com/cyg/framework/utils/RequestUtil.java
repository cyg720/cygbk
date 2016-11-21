package com.cyg.framework.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	public static boolean isAjaxRequest(HttpServletRequest request){
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request
				.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			return false;
		}
		return true;
	}
}
