package com.kstarrain.config;

import com.kstarrain.config.properties.SftpProperties;
import com.kstarrain.utils.SftpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Dong Yu
 * @create: 2018-10-12 14:12
 * @description: 手动注入dao配置
 */

@Configuration
public class UtilsConfig {

    @Autowired
    public UtilsConfig(SftpProperties sftpProperties) {
        SftpUtils.setSftpConfig(sftpProperties);
    }


}
