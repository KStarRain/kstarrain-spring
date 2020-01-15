//package com.kstarrain;
//
//import com.jcraft.jsch.ChannelSftp;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//
///**
// * 上传下载文件不好用
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:config/application.xml"})
//@Slf4j
//public class SftpRemoteFileTemplateTest extends AbstractJUnit4SpringContextTests {
//
//
//
//    @Autowired
//    private SftpRemoteFileTemplate sftpRemoteFileTemplate;
//
//
//
//    @Test
//    public void exists() {
//
//        log.info("===========================================");
//
//        System.out.println(sftpRemoteFileTemplate.exists("/upload/wx"));
//
//        log.info("===========================================");
//    }
//
//    @Test
//    public void list() {
//
//        log.info("===========================================");
//        ChannelSftp.LsEntry[] list = sftpRemoteFileTemplate.list("/");
//        for (ChannelSftp.LsEntry lsEntry : list) {
//            System.out.println(lsEntry.getFilename());
//        }
//        log.info("===========================================");
//    }
//
//    @Test
//    public void delete() {
//
//        log.info("===========================================");
//
//        System.out.println(sftpRemoteFileTemplate.remove("/upload/wx"));
//
//        log.info("===========================================");
//    }
//
//
//    @Test
//    public void rename() {
//
//        log.info("===========================================");
//
//        sftpRemoteFileTemplate.rename("/upload/wx2", "/upload/wx");
//
//        log.info("===========================================");
//    }
//
//
//}
