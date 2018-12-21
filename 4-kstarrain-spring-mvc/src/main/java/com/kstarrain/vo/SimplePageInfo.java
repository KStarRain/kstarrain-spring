package com.kstarrain.vo;

import java.io.Serializable;

/**
 * @author: Dong Yu
 * @create: 2018-12-21 09:56
 * @description:
 */
public class SimplePageInfo implements Serializable {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Boolean isCount= Boolean.TRUE;


    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getCount() {
        return this.isCount;
    }

    public void setCount(Boolean count) {
        this.isCount = count;
    }

}
