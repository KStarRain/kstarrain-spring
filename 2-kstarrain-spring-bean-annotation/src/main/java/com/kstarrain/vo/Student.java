package com.kstarrain.vo;

import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-10 10:56
 * @description:
 */
public class Student {

    private String name;

    private Date birthday;

    private Level level = Level.ONE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
