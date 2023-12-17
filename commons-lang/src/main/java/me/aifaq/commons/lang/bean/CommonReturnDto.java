package me.aifaq.commons.lang.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:29 2017/6/16
 */
@Data
@NoArgsConstructor
public class CommonReturnDto<T> {
	public static final String SUCCESS = "200";

	private String code = SUCCESS;
	private String message;
	private T data;

	public CommonReturnDto(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public CommonReturnDto(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return StringUtils.equals(code, SUCCESS);
	}
}
