package com.kstarrain.vo.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.kstarrain.vo.SimplePageInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2018-12-20 18:13
 * @description:
 */
@Data
public class TestRequest {

    @JsonUnwrapped
    private SimplePageInfo pageInfo;

    @NotNull(message = "用户名字不能为空")
    private String name;

    @NotNull(message = "用户年龄不能为空")
    private Integer age;

    private BigDecimal money;

    private Date birthday;

    private List<String> interest;
}
