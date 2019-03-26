package com.kstarrain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Dong Yu
 * @create: 2018-12-21 09:56
 * @description:
 */
@Data
public class SimplePageInfo implements Serializable {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Boolean isCount= Boolean.TRUE;
}
