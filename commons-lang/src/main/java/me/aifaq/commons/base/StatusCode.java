package me.aifaq.commons.base;

/**
 * 状态码
 *
 * @author eclipse
 * @since 15:33 2024/6/16
 */
public interface StatusCode {
    /**
     * 状态码
     */
    default int getCode() {
        return Response.SUCCESS;
    }

    /**
     * 状态描述
     */
    String getMessage();
}
