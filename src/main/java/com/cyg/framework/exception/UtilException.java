package com.cyg.framework.exception;

public class UtilException extends BaseException {
	private static final long	serialVersionUID	= 1L;

	public UtilException() {
		super();
	}

	public UtilException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public UtilException(String message) {
		super(message);
	}

	public UtilException(Throwable throwable) {
		super(throwable);
	}

	public UtilException(String message, String key) {
		super(message, key);
	}

	public UtilException(String message, String key, Object value) {
		super(message, key, value);
	}

	public UtilException(String message, String key, Object[] values) {
		super(message, key, values);
	}
}
