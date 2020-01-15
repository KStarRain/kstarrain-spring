package com.kstarrain.exception;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

    private String code;
    private String message;


    public BusinessException(String code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }



    public String getCode() {
        return code;
    }

    public JSON toJSON() {
        JSONObject json = new JSONObject();
        json.put("errorCode", code);
        json.put("errorMessage", message);
        return json;
    }

}
