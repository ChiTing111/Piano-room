package com.bookingsystem.exception;

import com.bookingsystem.pojo.Result;

public class BusinessException extends RuntimeException {
    private final Result result;

    public BusinessException(String message) {
        super(message);
        this.result = Result.error(message);
    }
    public BusinessException(Integer code, String message){
        super(message);
        this.result = Result.error(code,message);
    }

    public Result getResult() {
        return result;
    }
}