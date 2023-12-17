package me.aifaq.commons.lang.exception;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 22:09 2017/8/6
 */
public class NoRowsAffectedException extends RuntimeException {
	public NoRowsAffectedException() {
		super();
	}

	public NoRowsAffectedException(String message) {
		super(message);
	}

	public NoRowsAffectedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRowsAffectedException(Throwable cause) {
		super(cause);
	}
}
