package me.aifaq.commons.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SystemUtils;

/**
 * @author eclipse
 * @since 15:27 2024/6/16
 */
@Data
@NoArgsConstructor
public class Response {
    /**
     * 默认0表示响应成功
     * 可通过设置环境变量替换
     */
    public static final int SUCCESS;

    static {
        SUCCESS = Integer.parseInt(SystemUtils.getEnvironmentVariable("me.aifaq.response.success.code", "0"));
    }

    /**
     * 状态码
     */
    private int code = SUCCESS;
    /**
     * 状态描述
     */
    private String message;
    /**
     * 响应时间，默认当前时间
     */
    private long responseTime = System.currentTimeMillis();

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }

    public boolean isSuccess() {
        return code == SUCCESS;
    }

    public static Response success() {
        return new Response();
    }
}
