package com.cyg.framework.exception;

public class ControllerException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ControllerException() {
		super();
	}

	public ControllerException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Throwable throwable) {
		super(throwable);
	}

	public ControllerException(String message, String key) {
		super(message, key);
	}

	public ControllerException(String message, String key, Object value) {
		super(message, key, value);
	}

	public ControllerException(String message, String key, Object[] values) {
		super(message, key, values);
	}

}
