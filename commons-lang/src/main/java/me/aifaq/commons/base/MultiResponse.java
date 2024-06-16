package me.aifaq.commons.base;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author eclipse
 * @since 14:50 2024/2/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MultiResponse<T> extends SingleResponse<List<T>> {

    public MultiResponse() {
        super(Lists.newArrayList());
    }

    public MultiResponse(List<T> data) {
        super(data);
    }

    public MultiResponse(int code, String message) {
        super(code, message);
    }

    public MultiResponse(StatusCode statusCode) {
        super(statusCode);
    }
}
