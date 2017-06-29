package me.aifaq.commons.lang.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Wang Wei
 * @since 11:29 2017/6/16
 */
public class CommonReturnDto<T> {
	public static final String SUCCESS = "200";

	private String code = SUCCESS;
	private String message;
	private T data;

	public CommonReturnDto() {
	}

	public CommonReturnDto(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public CommonReturnDto(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return StringUtils.equals(code, SUCCESS);
	}
}
