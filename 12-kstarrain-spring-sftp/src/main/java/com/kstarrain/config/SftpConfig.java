//package com.kstarrain.com.kstarrain.config;
//
//import com.jcraft.jsch.ChannelSftp;
//import com.kstarrain.com.kstarrain.config.properties.SftpProperties;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.integration.file.remote.session.CachingSessionFactory;
//import org.springframework.integration.file.remote.session.SessionFactory;
//import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
//import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
//
///**
// * @author: DongYu
// * @create: 2019-12-23 15:51
// * @description:
// */
//@Configuration
//public class SftpConfig {
//
//    @Autowired
//    private SftpProperties sftpProperties;
//
//
//    @Bean
//    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
//
//        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
//        factory.setHost(sftpProperties.getHost());
//        factory.setPort(sftpProperties.getPort());
//        factory.setUser(sftpProperties.getUserName());
//        factory.setPassword(sftpProperties.getPassword());
//        factory.setAllowUnknownKeys(true);
//        CachingSessionFactory<ChannelSftp.LsEntry> cachingSessionFactory = new CachingSessionFactory<>(factory);
//        cachingSessionFactory.setPoolSize(10);
//        cachingSessionFactory.setSessionWaitTimeout(10000);
//        return cachingSessionFactory;
//    }
//
//
//    @Bean
//    public SftpRemoteFileTemplate sftpRemoteFileTemplate(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
//        return new SftpRemoteFileTemplate(sftpSessionFactory);
//    }
//
//}