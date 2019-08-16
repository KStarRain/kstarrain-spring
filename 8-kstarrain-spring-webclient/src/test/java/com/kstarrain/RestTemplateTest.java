package com.kstarrain;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kstarrain.response.ResultDTO;
import com.kstarrain.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class RestTemplateTest extends AbstractJUnit4SpringContextTests {

    final String requestUrl = "http://localhost:8080/servlet/httpclient";
    final String readFilePath = "E:" + File.separator + "其他" + File.separator + "cat.jpg";

    @Test
    public void doGetSimple() {

        RestTemplate restTemplate = new RestTemplate();

        String responseBodyStr = restTemplate.getForObject(requestUrl, String.class);
        System.out.println(responseBodyStr);

    }


    @Test
    public void doPostSimpleDefaultJsonUTF8() {

        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("userName", "董宇");
        requestBody.put("key", "1234qwer");

        RestTemplate restTemplate = new RestTemplate();
        String responseBodyStr = restTemplate.postForObject(requestUrl, requestBody, String.class);
        System.out.println(responseBodyStr);

    }


    @Test
    public void doGet1() {

        String url = HttpClientUtils.url(requestUrl)
                                    .addParam("userName", "貂蝉")
                                    .addParam("key", "1234qwer").build();


        HttpHeaders headers = HttpClientUtils.headers()
                                            .setContentType(MediaType.APPLICATION_JSON_UTF8)
                                            .addCookie("testMethod","GET")
                                            .addCookie("accessToken","2c81fd43-a991-4f78-bbce-21be2054431e_105502")
                                            .addCookie("key","吕布")
                                            .addParam(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString()).build();

        //只有response的ContentType为application/json时会自动转换
//        ResponseEntity<ResultDTO> responseEntity = HttpClientUtils.sendGet(url, headers, ResultDTO.class);
        ResponseEntity<String> responseEntity = HttpClientUtils.sendGet(url, headers, String.class);

        System.out.println(responseEntity.getHeaders().getContentType());
        if (responseEntity.getStatusCode() == HttpStatus.OK){
//            System.out.println(JSON.toJSONString(responseEntity.getBody()));
            System.out.println(responseEntity.getBody());
        } else {
            System.out.println(responseEntity.getStatusCode());
        }

    }


    @Test
    public void doGet2() {

        String url = requestUrl + "?userName={userName}&key={key}";

        Map<String,String> uriVariables = new HashMap();
        uriVariables.put("userName", "董宇");
        uriVariables.put("key", "1234qwer");


        HttpHeaders headers = HttpClientUtils.headers()
                                            .setContentType(MediaType.APPLICATION_JSON_UTF8)
                                            .addCookie("testMethod","GET")
                                            .addCookie("accessToken","2c81fd43-a991-4f78-bbce-21be2054431e_105502")
                                            .addCookie("key","吕布")
                                            .addParam(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString()).build();

        //只有response的ContentType为application/json时会自动转换
//        ResponseEntity<ResultDTO> responseEntity = HttpClientUtils.sendGet(url, headers, ResultDTO.class, uriVariables);
        ResponseEntity<String> responseEntity = HttpClientUtils.sendGet(url, headers, String.class, uriVariables);

        if (responseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println(responseEntity.getBody());
        } else {
            System.out.println(responseEntity.getStatusCode());
        }

    }


    @Test
    public void doPost_x_www_form_urlencoded() throws UnsupportedEncodingException {


        HttpHeaders headers = HttpClientUtils.headers()
//                                            .setContentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            .addCookie("testMethod","GET")
                                            .addCookie("accessToken","2c81fd43-a991-4f78-bbce-21be2054431e_105502")
                                            .addCookie("key","吕布")
                                            .addParam(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString())
                                            .addParam("store",Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8"))).build();


        MultiValueMap<String, Object> requestBody = HttpClientUtils.formBody()
                                                        .addParam("userName", "董宇")
                                                        .addParam("key", "1234qwer").build();

        ResponseEntity<ResultDTO> responseEntity = HttpClientUtils.sendPost(requestUrl, headers, requestBody, ResultDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println(responseEntity.getStatusCode());
            System.out.println(JSON.toJSONString(responseEntity.getBody()));
        } else {
            System.out.println(responseEntity.getStatusCode());
        }

    }


    @Test
    public void doPost_multipart_form_data() throws UnsupportedEncodingException {


        Map<String,String> headers = new HashMap();
//        headers.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.put("Cookie","testMethod=GET;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502");
        headers.put("Authorization","authorization_" + UUID.randomUUID().toString());
        headers.put("store",Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8")));


        MultiValueMap<String, Object> requestBody = HttpClientUtils.formBody()
                                                        .addParam("userName", "董宇")
                                                        .addParam("key", "1234qwer")
                                                        .addParam("file", new FileSystemResource(readFilePath)).build();

        ResponseEntity<String> responseEntity = HttpClientUtils.sendPost(requestUrl, headers, requestBody, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println(responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody());
        } else {
            System.out.println(responseEntity.getStatusCode());
        }
    }


    @Test
    public void doPostJson() throws UnsupportedEncodingException {

        Map<String,String> headers = new HashMap();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.put("Cookie","testMethod=GET;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502");
        headers.put("Authorization","authorization_" + UUID.randomUUID().toString());
        headers.put("store",Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8")));


        Map<String, Object> requestBody = HttpClientUtils.jsonBody()
                                            .addParam("userName", "董宇")
                                            .addParam("key", "1234qwer").build();


        ResponseEntity<ResultDTO> responseEntity = HttpClientUtils.sendPost(requestUrl, headers, requestBody, ResultDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println(JSON.toJSONString(responseEntity.getBody()));
        } else {
            System.out.println(responseEntity.getStatusCode());
        }

    }


}
