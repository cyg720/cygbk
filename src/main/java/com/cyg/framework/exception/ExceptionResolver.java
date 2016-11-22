package com.cyg.framework.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSONObject;
import com.cyg.framework.baseObject.AjaxResponseObject;
import com.cyg.framework.utils.LogUtil;
import com.cyg.framework.utils.RequestUtil;

/**
 * @Description: 全局异常处理。支持普通jsp页面和ajax请求
 * @author 王道兵
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		LogUtil.logError(ex.getMessage(), ex);
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			if (!RequestUtil.isAjaxRequest(request)) {
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {// JSON格式返回
				AjaxResponseObject resultObject = null;
				String message = ex.getMessage();
				if(ControllerException.class.isAssignableFrom(ex.getClass())){
					resultObject=new AjaxResponseObject(false, message);
				}else if(ServiceException.class.isAssignableFrom(ex.getClass())){
					resultObject=new AjaxResponseObject(false, message);
				}else if(DataAccessException.class.isAssignableFrom(ex.getClass())){
					resultObject=new AjaxResponseObject(false, "数据库时发生了异常,请刷新页面后再试试或联系管理员" );
				}else{
					resultObject = new AjaxResponseObject(false,"系统出现了异常,请联系管理员" );
				}
				try {
					response.getWriter().write(
							JSONObject.toJSONString(resultObject));
					response.getWriter().flush();
				} catch (Exception e) {
				}
				return null;
			}
		} else {
			return null;
		}
	}
}
