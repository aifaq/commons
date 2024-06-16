package me.aifaq.commons.base;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;
import java.util.function.Function;

/**
 * @author eclipse
 * @since 14:50 2024/2/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CursorResponse<Content, Cursor> extends SingleResponse<CursorResponse.CursorData<Content, Cursor>> {

    public CursorResponse() {
        super(new CursorData<>());
    }

    public CursorResponse(List<Content> content) {
        this(content, false, null);
    }

    public CursorResponse(List<Content> content, boolean hasNext, Function<Content, Cursor> cursorConvertor) {
        final CursorData<Content, Cursor> data = new CursorData<>();
        data.setContent(content);
        if (hasNext) {
            data.setCursor(cursorConvertor.apply(content.get(content.size() - 1)));
        } else {
            data.setCursor(null);
        }
        this.setData(data);
    }

    public CursorResponse(int code, String message) {
        super(code, message);
    }

    public CursorResponse(StatusCode statusCode) {
        super(statusCode);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CursorData<Content, Cursor> {
        /**
         * 内容列表
         */
        private List<Content> content = Lists.newArrayList();
        /**
         * 游标
         * 每次请求会返回新的游标内容，若返回null则说明已到底
         */
        private Cursor cursor;
    }
}
