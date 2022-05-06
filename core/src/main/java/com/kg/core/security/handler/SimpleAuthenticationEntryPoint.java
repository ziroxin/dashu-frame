package com.kg.core.security.handler;

import com.kg.component.utils.ResponseWriteUtils;
import com.kg.core.web.ResponseResult;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败拦截器
 *
 * @author ziro
 * @date 2022/5/5 22:44
 */
@Component
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String result = ResponseResult.builder()
                .code("" + HttpStatus.UNAUTHORIZED.value())
                .message("用户认证失败，请重新登录！")
                .toJsonString();
        ResponseWriteUtils.writeJson200(response, result);
    }
}
