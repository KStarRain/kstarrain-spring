package com.kstarrain.config;

import com.kstarrain.dao.ITestDao;
import com.kstarrain.dao.impl.Test1DaoImpl;
import com.kstarrain.dao.impl.Test2DaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Dong Yu
 * @create: 2018-10-12 14:12
 * @description: 手动注入dao配置
 */

@Configuration
public class DaoConfig {

    @Bean
    ITestDao test1DaoImpl(){
        return new Test1DaoImpl();
    }

    @Bean
    ITestDao test2DaoImpl(){
        return new Test2DaoImpl();
    }

}
