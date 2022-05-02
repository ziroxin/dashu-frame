package com.kg.core.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.core.annotation.IsResponseResult;
import com.kg.core.exception.BaseException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;

/**
 * 响应返回值处理
 *
 * @author ziro
 * @date 2022/5/2 10:27
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> containingClass = returnType.getContainingClass();
        // 判断：是否主动使用注解，IsResponseResult来禁用全局返回值
        if (hasDisabledResponseResult(returnType)) {
            return false;
        }

        // 忽略：Spring boot ErrorController
        if (ErrorController.class.isAssignableFrom(containingClass)) {
            return false;
        }

        // 无返回值，则使用全局返回值
        ResolvableType resolvableType = ResolvableType.forMethodParameter(returnType);
        Class<?> resolveClass = resolvableType.resolve();
        if (resolveClass == null) {
            return true;
        }

        // 忽略：本身就是ResponseResult
        if (resolveClass.isAssignableFrom(ResponseResult.class)) {
            return false;
        }

        // 忽略：ResponseEntity<ResponseResult<?>>
        if (resolveClass.isAssignableFrom(ResponseEntity.class)) {
            Class<?> genericClass = resolvableType.resolveGeneric(0);
            if (genericClass == null) {
                return true;
            }
            return !genericClass.isAssignableFrom(ResponseResult.class);
        }

        // 上面都不满足，默认使用全局返回值
        return true;
    }

    /**
     * 根据类以及方法上的注解，检查是否禁用返回结果封装
     *
     * @param returnType 方法信息
     * @return 如果禁用封装，则返回true，否则返回false
     */
    private boolean hasDisabledResponseResult(MethodParameter returnType) {
        // 检查类上的注解
        Class<?> containingClass = returnType.getContainingClass();
        IsResponseResult classAnnotation = AnnotatedElementUtils.findMergedAnnotation(containingClass,
                IsResponseResult.class);

        // 检查方法上的注解
        AnnotatedElement methodAnnotatedElement = returnType.getAnnotatedElement();
        IsResponseResult methodAnnotation = AnnotatedElementUtils.findMergedAnnotation(methodAnnotatedElement,
                IsResponseResult.class);

        // 有注解，且注解值为false时，代表禁用全局返回值
        boolean isClassDisabled = classAnnotation != null && !classAnnotation.value();
        boolean isMethodDisabled = methodAnnotation != null && !methodAnnotation.value();

        return isMethodDisabled || isClassDisabled;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        String typeName = returnType.getExecutable().getAnnotatedReturnType().getType().getTypeName();
        if (String.class.getName().equals(typeName)) {
            try {
                return objectMapper.writeValueAsString(ResponseResult.success(body));
            } catch (JsonProcessingException ex) {
                throw new BaseException("API: Convert return data occur exception", ex);
            }
        }

        return ResponseResult.success(body);
    }

}