package com.kstarrain.vo;

import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-22 15:51
 * @description:
 */
public class LogContent {

    private Level level = Level.INFO;
    private Exception exception = null;
    private String type;
    private String module;
    private String operating;
    private String operator;
    private String content;
    private Date operateDate;

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setOperating(String operating) {
        this.operating = operating;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public Level getLevel() {
        return this.level;
    }

    public Exception getException() {
        return this.exception;
    }

    public String getType() {
        return this.type;
    }

    public String getModule() {
        return this.module;
    }

    public String getOperating() {
        return this.operating;
    }

    public String getOperator() {
        return this.operator;
    }

    public String getContent() {
        return this.content;
    }

    public Date getOperateDate() {
        return this.operateDate;
    }


}
