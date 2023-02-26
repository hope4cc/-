package com.paopao.exception;

public class GlobalException extends Exception {

    public GlobalException() {
        super("系统异常,请联系管理员");
    }

    public GlobalException(String message) {
        super(message);
    }
}
