package me.aifaq.commons.lang;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 17:32 2017/9/29
 */
public class ResponseUtil {
    public static void writeJson(HttpServletResponse response, String str) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.toString());

        try (PrintWriter writer = response.getWriter()) {
            writer.write(str);
            writer.flush();
        }
    }
}
