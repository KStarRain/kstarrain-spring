package com.kstarrain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2019-08-19 17:47
 * @description:
 */
@Data
public class LoginRequest {

    private String userName;

    private String password;

    private String date;

    private List<String> tags;



}
