package com.kstarrain.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: DongYu
 * @create: 2019-12-23 16:07
 * @description: sftp 配置信息
 */
@Component
@PropertySource("classpath:properties/sftp.properties")
@Data
public class SftpProperties {

    /** 主机地址 */
    @Value("${spring.sftp.host}")
    private String host;

    /** 端口号 */
    @Value("${spring.sftp.port}")
    private int port;

    /** 协议 */
    @Value("${spring.sftp.protocol}")
    private String protocol;

    /** 主机用户名 */
    @Value("${spring.sftp.username}")
    private String userName;

    /** 主机密码 */
    @Value("${spring.sftp.password}")
    private String password;

    /** 私钥 */
    @Value("${spring.sftp.privateKey}")
    private String privateKey;

    /** 口令 */
    @Value("${spring.sftp.passphrase}")
    private String passphrase;

    @Value("${spring.sftp.sessionStrictHostKeyChecking}")
    private String sessionStrictHostKeyChecking;

    @Value("${spring.sftp.sessionConnectTimeout}")
    private Integer sessionConnectTimeout;

    @Value("${spring.sftp.channelConnectedTimeout}")
    private Integer channelConnectedTimeout;


}
