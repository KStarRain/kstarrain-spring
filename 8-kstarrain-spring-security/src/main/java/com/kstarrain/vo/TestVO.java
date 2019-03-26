package com.kstarrain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2018-12-20 18:13
 * @description:
 */
@Data
public class TestVO {

    private String name;
    private Integer age;
    private BigDecimal money;
    private Date time;
    private List<String> interest;
}
