package org.ma.db.jdbc.exception;

public class CommondbException extends RuntimeException {

	private static final long serialVersionUID = 112L;

	public CommondbException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommondbException(Throwable cause) {
		super(cause);
	}

	public CommondbException() {
		super();
	}

	public CommondbException(String message) {
		super(message);
	}

}
