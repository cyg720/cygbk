package com.cyg.framework.baseObject;

import java.io.Serializable;

/**
* @Description: 值对象，用于承载业务数据。   
* @author 王道兵 
*/
public class AjaxResponseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Boolean success;//是否成功
	
	private Boolean autoPrompt=true;//页面是否自动弹出消息提示。

	private String message;//提示信息
	
	private Object content;	//数据
	
	public AjaxResponseObject (){
	}
	
	public AjaxResponseObject (boolean success,String message){
		this.success=success;
		this.message=message;
	}
	
	public AjaxResponseObject (boolean success,String message,Boolean autoPrompt){
		this.success=success;
		this.message=message;
		this.autoPrompt=autoPrompt;
	}
	
	public AjaxResponseObject(boolean success,Object content){
		this.success=success;
		this.content=content;
	}
	
	public AjaxResponseObject(boolean success,String message,Object content){
		this.success=success;
		this.message=message;
		this.content=content;
	}
	public AjaxResponseObject(boolean success,String message,Object content,Boolean autoPrompt){
		this.success=success;
		this.message=message;
		this.content=content;
		this.autoPrompt=autoPrompt;
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Boolean getAutoPrompt() {
		return autoPrompt;
	}

	public void setAutoPrompt(Boolean autoPrompt) {
		this.autoPrompt = autoPrompt;
	}
}
