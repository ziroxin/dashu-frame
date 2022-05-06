package com.kg.core.security.handler;

import com.kg.component.utils.ResponseWriteUtils;
import com.kg.core.web.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问被拒绝拦截器
 *
 * @author ziro
 * @date 2022/5/5 22:50
 */
@Component
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String result = ResponseResult.builder()
                .code("" + HttpStatus.FORBIDDEN.value())
                .message("访问被拒绝！您的权限不足")
                .toJsonString();
        ResponseWriteUtils.writeJson200(response, result);
    }
}
