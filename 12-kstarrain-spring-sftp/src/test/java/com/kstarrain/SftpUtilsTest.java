package com.kstarrain;

import com.kstarrain.utils.SftpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayInputStream;
import java.io.File;


/**
 * 上传下载文件不好用
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class SftpUtilsTest extends AbstractJUnit4SpringContextTests {

    private String filePath = "E:" + File.separator + "opt" + File.separator + "data" + File.separator + "wechat" + File.separator +"bill"+ File.separator + "helpRepay";
    private String fileName = "wx_dr_20191222.txt";

    @Test
    public void uploadFile01() throws Exception {

        log.info("===========================================");

        byte[] bytes = FileUtils.readFileToByteArray(new File(filePath + File.separator + fileName));
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        SftpUtils.uploadFile("/upload/wx/wx/wx/wx_dr_20191222.txt", inputStream);

        log.info("===========================================");
    }


    @Test
    public void uploadFile02() throws Exception {

        log.info("===========================================");

        SftpUtils.uploadFile("/upload/wx/wx/wx/wx_dr_20191222.txt", new File(filePath + File.separator + fileName));

        log.info("===========================================");
    }


    @Test
    public void downLoadFile() throws Exception {

        log.info("===========================================");

        File file = SftpUtils.downloadFile("/upload/wx/wx/wx/wx_dr_20191222.txt", filePath + File.separator + "test.txt");

        log.info("===========================================");
    }



}
