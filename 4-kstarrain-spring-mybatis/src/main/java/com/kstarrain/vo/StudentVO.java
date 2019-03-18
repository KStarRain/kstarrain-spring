package com.kstarrain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description:
 */
@Data
public class StudentVO {

    String id;

    String name;

    Date birthday;

    BigDecimal money;

}
