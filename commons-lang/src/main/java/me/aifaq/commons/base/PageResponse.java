package me.aifaq.commons.base;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

/**
 * @author eclipse
 * @since 14:50 2024/2/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageResponse<Content> extends SingleResponse<PageResponse.PageData<Content>> {

    public PageResponse() {
        super(new PageData<>());
    }

    public PageResponse(int code, String message) {
        super(code, message);
    }

    public PageResponse(StatusCode statusCode) {
        super(statusCode);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageData<Content> {
        /**
         * 总记录数
         */
        private long total;
        /**
         * 内容列表
         */
        private List<Content> content = Lists.newArrayList();
    }
}
