package com.kstarrain;


import com.kstarrain.utils.HttpClientUtils;
import com.kstarrain.vo.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class TestControllerHttpTest extends AbstractJUnit4SpringContextTests {

    final String uploadPath = "E:" + File.separator + "其他" + File.separator + "cat.jpg";

    String copyFilePath = "E:" + File.separator + "test" + File.separator + "file" + File.separator + "cat_copy_server.jpg";

    @Test
    public void sendGet() {

        String url = HttpClientUtils.url("http://localhost:8080/mvc/http/get/{id}")
                            .addParam("id",2)
                            .addParam("userName", "貂蝉")
                            .addParam("password", "1234qwer").build();

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id",1);

        HttpHeaders headers = HttpClientUtils.headers()
//                                .setContentType(MediaType.APPLICATION_JSON_UTF8)
                                .addCookie("testMethod","GET")
                                .addCookie("accessToken","2c81fd43-a991-4f78-bbce-21be2054431e_105502")
                                .addCookie("company","万达")
                                .addParam(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString()).build();

//        ResponseEntity<String> responseEntity  = HttpClientUtils.sendGet(url, String.class);
        ResponseEntity<String> responseEntity  = HttpClientUtils.sendGet(url, headers, String.class, uriVariables);
        StandardCharsets.UTF_8.name();
        System.out.println(responseEntity.getHeaders().getContentType());
        if (responseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println(responseEntity.getBody());
        } else {
            System.out.println(responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody());
        }
    }


    @Test
    public void doPostEmpty() {

        String url = HttpClientUtils.url("http://localhost:8080/mvc/http/postEmpty")
                                    .addParam("time", System.currentTimeMillis()).build();

        HttpHeaders headers = HttpClientUtils.headers()
                                . setContentType(MediaType.APPLICATION_JSON_UTF8)
                                    .addCookie("testMethod","POST")
                                    .addCookie("accessToken","2c81fd43-a991-4f78-bbce-21be2054431e_105502")
                                    .addCookie("company","万达")
                                    .addParam(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString()).build();

        ResponseEntity<String> responseEntity = HttpClientUtils.sendPost(url, headers, String.class);

        System.out.println(responseEntity.getHeaders().getContentType());
        if (responseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println(responseEntity.getBody());
        } else {
            System.out.println(responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody());
        }

    }



    @Test
    public void doPostJson() {

        String url = HttpClientUtils.url("http://localhost:8080/mvc/http/postJson")
                                    .addParam("time", System.currentTimeMillis()).build();


        HttpHeaders headers = HttpClientUtils.headers()
//                                .setContentType(MediaType.APPLICATION_JSON_UTF8)
                                .addCookie("testMethod","POST")
                                .addCookie("accessToken","2c81fd43-a991-4f78-bbce-21be2054431e_105502")
                                .addCookie("company","万达")
                                .addParam(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString()).build();

        LoginRequest requestBody = new LoginRequest();
        requestBody.setUserName("董宇");
        requestBody.setPassword("1234qwer");

       /* Map<String, Object> requestBody = HttpClientUtils.jsonBody()
                                                .addParam("userName", "董宇")
                                                .addParam("password", "1234qwer").build();*/


        ResponseEntity<String> responseEntity = HttpClientUtils.sendPost(url, headers, requestBody, String.class);

        System.out.println(responseEntity.getHeaders().getContentType());
        if (responseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println(responseEntity.getBody());
        } else {
            System.out.println(responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody());
        }
    }


    @Test
    public void doPostForm() {

//        String url = "http://localhost:8080/mvc/http/postForm1";
        String url = "http://localhost:8080/mvc/http/postForm2";
//        url = HttpClientUtils.url(url)
//                .addParam("date", System.currentTimeMillis()).build();

        HttpHeaders headers = HttpClientUtils.headers()
//                                .setContentType(MediaType.APPLICATION_FORM_URLENCODED)
                                    .addCookie("testMethod","POST")
                                    .addCookie("accessToken","2c81fd43-a991-4f78-bbce-21be2054431e_105502")
                                    .addCookie("company","万达")
                                    .addParam(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString()).build();


        MultiValueMap<String, Object> requestBody = HttpClientUtils.formBody()
                                                    .addParam("date", "2018-05-01")
                                                    .addParam("tags", "红,黄")
                                                    .addParam("userName", "貂蝉")
                                                    .addParam("password", "1234qwer").build();


        ResponseEntity<String> responseEntity = HttpClientUtils.sendPost(url, headers, requestBody, String.class);

        System.out.println(responseEntity.getHeaders().getContentType());
        if (responseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println(responseEntity.getBody());
        } else {
            System.out.println(responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody());
        }
    }



    @Test
    public void doPostMultipart() {

        String url = "http://localhost:8080/mvc/http/postMultipart";


        HttpHeaders headers = HttpClientUtils.headers()
                                .setContentType(MediaType.MULTIPART_FORM_DATA)
                                .addCookie("testMethod","POST")
                                .addCookie("accessToken","2c81fd43-a991-4f78-bbce-21be2054431e_105502")
                                .addCookie("company","万达")
                                .addParam(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString()).build();


        MultiValueMap<String, Object> requestBody = HttpClientUtils.formBody()
                                                        .addParam("date", "2018-05-01")
                                                        .addParam("tags", "红,黄")
                                                        .addParam("userName", "貂蝉")
                                                        .addParam("password", "1234qwer")
                                                        .addParam("file", new FileSystemResource(uploadPath)).build();


        ResponseEntity<String> responseEntity = HttpClientUtils.sendPost(url, headers, requestBody, String.class);

        System.out.println(responseEntity.getHeaders().getContentType());
        if (responseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println(responseEntity.getBody());
        } else {
            System.out.println(responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody());
        }
    }



    @Test
    public void doGetLogo() {

        String logoUrl = "http://localhost:8080/mvc/http/logo";


        try {
            URL url = new URL(logoUrl);
//            IOUtils.copy(url.openStream(), new FileOutputStream(new File(copyFilePath)));

            FileUtils.copyURLToFile(url,new File(copyFilePath));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }


    }




}
