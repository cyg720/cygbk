package com.cyg.framework.exception;

public class ServiceException extends BaseException {
	
    private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable throwable) {
		super(throwable);
	}

	public ServiceException(String message, String key) {
		super(message, key);
	}

	public ServiceException(String message, String key, Object value) {
		super(message, key, value);
	}

	public ServiceException(String message, String key, Object[] values) {
		super(message, key, values);
	}
}
