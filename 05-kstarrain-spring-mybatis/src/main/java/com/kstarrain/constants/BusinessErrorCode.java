package com.kstarrain.constants;

import com.kstarrain.exception.ErrorCode;

public enum BusinessErrorCode implements ErrorCode {

    BUSINESS000("BUSINESS000", "系统异常"),
    BUSINESS001("BUSINESS001", "采购数必须为正数"),
    BUSINESS002("BUSINESS002", "商品不存在"),
    BUSINESS003("BUSINESS003", "采购量不能大于库存量"),
    BUSINESS004("BUSINESS004", "抢购失败，请重新抢购"),
    BUSINESS005("BUSINESS005", "商品已售光"),


    ;

    private String code;
    private String message;

    BusinessErrorCode(String code, String message) {
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


}
