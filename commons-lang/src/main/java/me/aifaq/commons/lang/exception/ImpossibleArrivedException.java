package me.aifaq.commons.lang.exception;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:29 2017/8/14
 */
public class ImpossibleArrivedException extends RuntimeException {
	public ImpossibleArrivedException() {
		super();
	}

	public ImpossibleArrivedException(String message) {
		super(message);
	}

	public ImpossibleArrivedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImpossibleArrivedException(Throwable cause) {
		super(cause);
	}
}
