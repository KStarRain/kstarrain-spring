package com.kstarrain.vo.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.kstarrain.vo.SimplePageInfo;
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

    @JsonUnwrapped
    private SimplePageInfo pageInfo;

    private String name;

    private Integer age;

    private BigDecimal money;

    private Date birthday;

    private List<String> interest;

}
