package com.kg.core.exception;

import com.kg.core.web.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 * <pre>
 *     400：客户端错误（如参数数量、参数格式，等错误）
 *     405：请求类型异常（如：POST请求却使用GET，等错误）
 *     500：服务器端错误
 * </pre>
 *
 * @author ziro
 * @date 2022/5/2 10:22
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 自定义全局异常处理 200 但返回错误码500
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<Object> handleBaseException(BaseException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseResult.error(ex.getMessage());
    }

    // 默认异常处理 500
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult<Object> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseResult.error(ex.getMessage());
    }

    // 文件大小超限 500
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseResult.error("上传文件大小超限制！");
    }

    // 数据库错误 500
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult<Object> handleSQLException(MaxUploadSizeExceededException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseResult.error("数据库错误");
    }

    // 请求类型错误 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseResult<Object> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseResult.builder()
                .code("" + HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .data(ex.getMessage())
                .build();
    }

    // BindException（例如：端口被占用） 400
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult<Object> handleBindException(BindException ex) {
        return handleBindingResult(ex.getBindingResult());
    }

    // 方法参数无效 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return handleBindingResult(ex.getBindingResult());
    }

    private ResponseResult<Object> handleBindingResult(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return ResponseResult.builder()
                .code("" + HttpStatus.BAD_REQUEST)
                .message(fieldErrors == null ? "参数错误" : fieldErrors.get(0).getDefaultMessage())
                .data(String.format("Error count: %", bindingResult.getErrorCount()))
                .build();
    }

    // 参数格式不正确 400
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        StringBuilder message = new StringBuilder();
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            for (ConstraintViolation constraintViolation : constraintViolations) {
                message.append(constraintViolation.getMessage());
            }
        }
        message.insert(0, "参数格式不正确：");
        return ResponseResult.builder()
                .code("" + HttpStatus.BAD_REQUEST)
                .message(message.toString())
                .build();
    }
}
