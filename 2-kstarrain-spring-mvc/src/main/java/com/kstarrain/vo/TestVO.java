package com.kstarrain.vo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2018-12-20 18:13
 * @description:
 */
public class TestVO {

    @JsonUnwrapped
    private SimplePageInfo pageInfo;

    private String name;
    private Integer age;
    private BigDecimal money;
    private Date time;
    private List<String> interest;

    public SimplePageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(SimplePageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }
}
