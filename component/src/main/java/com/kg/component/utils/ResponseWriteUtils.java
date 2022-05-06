package com.kg.component.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 输出响应工具
 *
 * @author ziro
 * @date 2022/5/6 20:19
 */
public class ResponseWriteUtils {

    /**
     * 响应：状态200，json格式
     */
    public static void writeJson200(HttpServletResponse response, Object body) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(body);
    }
}
