package com.cyg.framework.exception;

/**
 * @Description: 异常信息类,所有业务异常需继承此异常。
 * @author 王道兵
 */
public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	// 异常代码
	private String key;
	
	private Object[] values;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable throwable) {
		super(throwable);
	}

	public BaseException(String message, String key) {
		super(message);
		this.key = key;
	}

	public BaseException(String message, String key, Object value) {
		super(message);
		this.key = key;
		this.values = new Object[] { value };
	}

	public BaseException(String message, String key, Object[] values) {
		super(message);
		this.key = key;
		this.values = values;
	}

	public String getKey() {
		return key;
	}

	public Object[] getValues() {
		return values;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
}
