package com.greenkoo.exception;

public class IllegalAdvertException extends Exception {

	private static final long serialVersionUID = 1L;

	public IllegalAdvertException() {
		super();
	}

	public IllegalAdvertException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalAdvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalAdvertException(String message) {
		super(message);
	}

	public IllegalAdvertException(Throwable cause) {
		super(cause);
	}
}
