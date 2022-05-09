package com.kg.core.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 全局返回值
 * <p>
 * 使用方法 1 - withCode构造器：
 * <pre>
 * {@code
 *     ResponseResult<String> result = ResponseResult.withCode("1000")
 *          .message("OK")
 *          .data("demo")
 *          .build();
 * }
 * </pre>
 * <p>
 * 使用方法 2 - 成功/失败构造器:
 * <pre>
 * {@code
 *     ResponseResult.success("success");
 * }
 * {@code
 *     ResponseResult.error("error");
 * }
 * </pre>
 * <p>
 * 使用方法 3 - 基础构造器:
 * <pre>
 * {@code
 *     ResponseResult.builder()
 *          .code("200")
 *          .message("OK")
 *          .data("demo")
 *          .build();
 * }
 * </pre>
 *
 * @author ziro
 * @date 2022/5/2 10:27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    // 响应代码
    private String code;
    // 提示信息
    private String message;
    // 响应时间
    @JSONField(serialize = false)
    private LocalDateTime timestamp = LocalDateTime.now();
    // 响应数据
    private T data;

    /**
     * 请求成功构造器
     */
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * 请求成功构造器，有data
     */
    public static <T> ResponseResult<T> success(T data) {
        return ResponseResult.<T>builder()
                .code(CommonResult.SUCCESS.code)
                .message(CommonResult.SUCCESS.message)
                .data(data)
                .build();
    }

    /**
     * 请求失败构造器
     */
    public static <T> ResponseResult<T> error() {
        return error(null);
    }

    /**
     * 请求失败构造器，有data
     */
    public static ResponseResult error(String msg) {
        return ResponseResult.builder()
                .code(CommonResult.ERROR.code)
                .message(msg == null ? CommonResult.ERROR.message : msg)
                .build();
    }

    /**
     * withCode 构造器
     */
    public static <T> ResponseResultBuilder<T> withCode(String code) {
        return ResponseResult.<T>builder().code(code);
    }

    /**
     * 基础构造器
     */
    public static <T> ResponseResultBuilder<T> builder() {
        return new ResponseResultBuilder<>();
    }

    public String toString() {
        return "ResponseResult(code=" + this.code
                + ", message=" + this.message
                + ", timestamp=" + this.timestamp
                + ", data=" + this.code + ")";
    }

    /**
     * 响应成功/失败枚举
     */
    @Getter
    @AllArgsConstructor
    enum CommonResult {
        SUCCESS("200", "Success"), ERROR("500", "Error");
        private final String code;
        private final String message;
    }

    /**
     * 响应构造器实现
     */
    public static class ResponseResultBuilder<T> {

        private String code;

        private String message;

        private LocalDateTime timestamp = LocalDateTime.now();

        private T data;

        public ResponseResultBuilder<T> code(String code) {
            this.code = code;
            return this;
        }

        public ResponseResultBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResponseResultBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseResult<T> build() {
            return new ResponseResult<>(this.code, this.message, this.timestamp, this.data);
        }

        @Override
        public String toString() {
            return JSON.toJSONString(new ResponseResult<>(this.code, this.message, this.timestamp, this.data));
        }
    }
}
