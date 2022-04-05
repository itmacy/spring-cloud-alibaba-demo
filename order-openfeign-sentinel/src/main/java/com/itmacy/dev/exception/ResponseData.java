package com.itmacy.dev.exception;

/**
 * author: itmacy
 * date: 2022/3/29
 */
public class ResponseData<T> {

    private Integer code;
    private String message;
    private T data;

    public ResponseData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }
    public static ResponseData error (Integer code, String message) {
        return new ResponseData(code, message);
    }

    public void setData(T data) {
        this.data = data;
    }
}
