package com.kstarrain.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.exception.CommonErrorCode;
import com.kstarrain.response.ResultDTO;
import com.kstarrain.vo.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @RequestMapping注解中
 *    consumes属性：request的headers中Content-Type必须匹配，否则无法进入controller
 *    produces属性：response的headers中Content-Type的值
 * @ResponseBody  的具体作用：
 *      其将方法的返回值通过适当的转换器转换为指定的格式之后，写入到 response 对象的 body 区，通常用来给客户端返回 JSON 数据或者是 XML 数据，
 *      当方法上面没有写 @ResponseBody 时，底层会将方法的返回值封装为 ModelAndView 对象；
 *      需要注意的是，在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过 response 对象输出指定格式的数据
 *    如果返回值为String  response的headers中Content-Type的值默认为text/plain;charset=ISO-8859-1（汉字会有乱码，可以用produces属性指定）
 *    如果返回值为对象    response的headers中Content-Type的值默认为application/json;charset=UTF-8
 */
@Controller
@Slf4j
public class TestController {


    private String directoryPath = "E:" + File.separator + "test" + File.separator + "file";

    private String readFilePath = "E:" + File.separator + "其他" + File.separator + "cat.jpg";


    @GetMapping(value = "/http/get/{id}"
//            ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
//            ,produces = "text/plain;charset=UTF-8"
            ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public ResponseEntity<JSON> get(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @PathVariable(value = "id",required = false) String id1,
                                    @RequestParam(value = "id",required = false) String id2,
                                    @RequestParam(value = "userName",required = false) String userName,
                                    @RequestParam(value = "password",required = false) String password,
                                    @CookieValue(value = "testMethod",required = false) String testMethod,
                                    @CookieValue(value = "accessToken",required = false) String accessToken,
                                    @CookieValue(value = "company",required = false) String company) {

        try {

            System.out.println("Content-Type        : " + request.getHeader(HttpHeaders.CONTENT_TYPE));
            System.out.println("Accept              : " + request.getHeader(HttpHeaders.ACCEPT));
            System.out.println("Authorization       : " + request.getHeader(HttpHeaders.AUTHORIZATION));

            System.out.println("Cookie              : testMethod=" + testMethod);
            System.out.println("Cookie              : accessToken=" + accessToken);
            System.out.println("Cookie              : company=" + company);

            System.out.println("id1                  : " + id1);
            System.out.println("id2                  : " + id2);
            System.out.println("userName            : " + userName);
            System.out.println("password            : " + password);



            ResultDTO resultDTO = new ResultDTO<>(true,"请求成功",Lists.newArrayList());

            throw new BusinessException(CommonErrorCode.SYSTEM_ERROR);

//            return ResponseEntity.ok().body((JSON)JSON.toJSON(resultDTO));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }


    @PostMapping(value = "/http/postEmpty")
    @ResponseBody
    public ResponseEntity<JSON> postEmpty(HttpServletRequest request,
                                         HttpServletResponse response,
                                          @RequestParam(value = "time",required = false) Long time,
                                          @CookieValue(value = "testMethod",required = false) String testMethod,
                                          @CookieValue(value = "accessToken",required = false) String accessToken,
                                          @CookieValue(value = "company",required = false) String company) {

        try {

            System.out.println("Content-Type        : " + request.getHeader(HttpHeaders.CONTENT_TYPE));
            System.out.println("Accept              : " + request.getHeader(HttpHeaders.ACCEPT));
            System.out.println("Authorization       : " + request.getHeader(HttpHeaders.AUTHORIZATION));

            System.out.println("Cookie              : testMethod=" + testMethod);
            System.out.println("Cookie              : accessToken=" + accessToken);
            System.out.println("Cookie              : company=" + company);

            System.out.println("time                : " + time);

            ResultDTO resultDTO = new ResultDTO<>(true,"请求成功",Lists.newArrayList());

            return ResponseEntity.ok().body((JSON)JSON.toJSON(resultDTO));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }


    @PostMapping(value = "/http/postJson" ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<JSON> postJson(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam(value = "time",required = false) Long time,
                                         @CookieValue(value = "testMethod",required = false) String testMethod,
                                         @CookieValue(value = "accessToken",required = false) String accessToken,
                                         @CookieValue(value = "company",required = false) String company,
                                         @RequestBody LoginRequest loginRequest) {

        try {

            System.out.println("Content-Type        : " + request.getHeader(HttpHeaders.CONTENT_TYPE));
            System.out.println("Accept              : " + request.getHeader(HttpHeaders.ACCEPT));
            System.out.println("Authorization       : " + request.getHeader(HttpHeaders.AUTHORIZATION));

            System.out.println("Cookie              : testMethod=" + testMethod);
            System.out.println("Cookie              : accessToken=" + accessToken);
            System.out.println("Cookie              : company=" + company);

            System.out.println("time                : " + time);

            System.out.println("userName            : " + loginRequest.getUserName());
            System.out.println("password            : " + loginRequest.getPassword());


            ResultDTO resultDTO = new ResultDTO<>(true,"请求成功",Lists.newArrayList());

            return ResponseEntity.ok().body((JSON)JSON.toJSON(resultDTO));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }


    @PostMapping(value = "/http/postForm1")
    @ResponseBody
    public ResponseEntity<JSON> postForm1(HttpServletRequest request,
                                          HttpServletResponse response,
                                          @CookieValue(value = "testMethod",required = false) String testMethod,
                                          @CookieValue(value = "accessToken",required = false) String accessToken,
                                          @CookieValue(value = "company",required = false) String company,
                                          @RequestParam(value = "userName") String userName,
                                          @RequestParam(value = "password") String password,
                                          @RequestParam(value = "date",required = false) String date,
                                          @RequestParam(value = "tags") List<String> tags) {

        try {

            System.out.println("Content-Type        : " + request.getHeader(HttpHeaders.CONTENT_TYPE));
            System.out.println("Accept              : " + request.getHeader(HttpHeaders.ACCEPT));
            System.out.println("Authorization       : " + request.getHeader(HttpHeaders.AUTHORIZATION));

            System.out.println("Cookie              : testMethod=" + testMethod);
            System.out.println("Cookie              : accessToken=" + accessToken);
            System.out.println("Cookie              : company=" + company);


            System.out.println("userName            : " + userName);
            System.out.println("password            : " + password);
            System.out.println("date[非必传]         : " + date);
            System.out.println("tags                : " + tags);


            ResultDTO resultDTO = new ResultDTO<>(true,"请求成功",Lists.newArrayList());

            return ResponseEntity.ok().body((JSON)JSON.toJSON(resultDTO));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }




    @PostMapping(value = "/http/postForm2")
    @ResponseBody
    public ResponseEntity<JSON> postForm2(HttpServletRequest request,
                                          HttpServletResponse response,
                                          @CookieValue(value = "testMethod",required = false) String testMethod,
                                          @CookieValue(value = "accessToken",required = false) String accessToken,
                                          @CookieValue(value = "company",required = false) String company,
                                          LoginRequest loginRequest ) {

        try {

            System.out.println("Content-Type        : " + request.getHeader(HttpHeaders.CONTENT_TYPE));
            System.out.println("Accept              : " + request.getHeader(HttpHeaders.ACCEPT));
            System.out.println("Authorization       : " + request.getHeader(HttpHeaders.AUTHORIZATION));

            System.out.println("Cookie              : testMethod=" + testMethod);
            System.out.println("Cookie              : accessToken=" + accessToken);
            System.out.println("Cookie              : company=" + company);

            System.out.println("userName            : " + loginRequest.getUserName());
            System.out.println("password            : " + loginRequest.getPassword());
            System.out.println("date[非必传]         : " + loginRequest.getDate());
            System.out.println("tags                : " + loginRequest.getTags());


            ResultDTO resultDTO = new ResultDTO<>(true,"请求成功",Lists.newArrayList());

            return ResponseEntity.ok().body((JSON)JSON.toJSON(resultDTO));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }



    @PostMapping(value = "/http/postMultipart")
    @ResponseBody
    public ResponseEntity<JSON> postMultipart(HttpServletRequest request,
                                              HttpServletResponse response,
                                              @CookieValue(value = "testMethod",required = false) String testMethod,
                                              @CookieValue(value = "accessToken",required = false) String accessToken,
                                              @CookieValue(value = "company",required = false) String company,
                                              @RequestParam(value = "userName",required = false) String userName,
                                              @RequestParam(value = "password",required = false) String password,
                                              @RequestParam(value = "date",required = false) String date,
                                              @RequestParam(value = "tags",required = false) List<String> tags,
                                              @RequestParam(value = "file") MultipartFile multipartFile) {

        try {

            System.out.println("Content-Type        : " + request.getHeader(HttpHeaders.CONTENT_TYPE));
            System.out.println("Accept              : " + request.getHeader(HttpHeaders.ACCEPT));
            System.out.println("Authorization       : " + request.getHeader(HttpHeaders.AUTHORIZATION));

            System.out.println("Cookie              : testMethod=" + testMethod);
            System.out.println("Cookie              : accessToken=" + accessToken);
            System.out.println("Cookie              : company=" + company);


            System.out.println("userName            : " + userName);
            System.out.println("password            : " + password);
            System.out.println("date[非必传]         : " + date);
            System.out.println("tags                : " + tags);


            response.setHeader("Accept-Ranges","bytes");
            response.setHeader("Cache-Control","no-store");

            String fileName = multipartFile.getOriginalFilename();

            String contentType = "";
            if (fileName.endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG_VALUE;
            }else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".jpe")) {
                contentType = MediaType.IMAGE_JPEG_VALUE;
            }else if (fileName.endsWith(".gif")) {
                contentType = MediaType.IMAGE_GIF_VALUE;
            }

            response.setContentType(contentType);

            InputStream inputStream = multipartFile.getInputStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            String imageBase64 = new String(Base64Utils.encode(bytes));
            System.out.println("imageBase64:        " + imageBase64);
            /**将传入的文件复制输出到本地上*/
            FileUtils.forceMkdir(new File(directoryPath));
            String writeFilePath = directoryPath + File.separator + multipartFile.getOriginalFilename();
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),new File(writeFilePath));

            ResultDTO resultDTO = new ResultDTO<>(true,"请求成功",Lists.newArrayList());

            return ResponseEntity.ok().body((JSON)JSON.toJSON(resultDTO));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }

}
