package me.aifaq.commons.lang.exception;

/**
 * @author Wang Wei
 * @since 11:18 2017/6/16
 */
public class MessageException extends RuntimeException {
	private final String code;
	private Object[] args;

	public MessageException(String code, String message, Object... args) {
		super(message);
		this.code = code;
		this.args = args;
	}

	public String getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}
