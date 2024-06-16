package me.aifaq.commons.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author eclipse
 * @since 14:50 2024/2/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SingleResponse<D> extends Response {

    /**
     * 响应数据
     */
    private D data;


    public SingleResponse(D data) {
        this.data = data;
    }

    public SingleResponse(int code, String message) {
        super(code, message);
    }

    public SingleResponse(StatusCode statusCode) {
        super(statusCode);
    }

    public static <D> SingleResponse<D> success(D data) {
        return new SingleResponse<>(data);
    }

}
