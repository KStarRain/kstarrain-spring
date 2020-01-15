package com.kstarrain.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public enum CommonErrorCode implements ErrorCode {

    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    ;

    private String code;
    private String message;

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public JSON toJSON() {
        JSONObject json = new JSONObject();
        json.put("errorCode", code);
        json.put("errorMessage", message);
        return json;
    }


}
