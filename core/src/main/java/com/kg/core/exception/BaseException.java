package com.kg.core.exception;

/**
 * 全局异常
 *
 * @author ziro
 * @date 2022/5/2 10:01
 */
public class BaseException extends Exception {
    private static final long serialVersionUID = 1L;

    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
