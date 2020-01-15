package com.kstarrain.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.exception.CommonErrorCode;
import com.kstarrain.response.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Controller
@Slf4j
public class StreamController {


    private String directoryPath = "E:" + File.separator + "test" + File.separator + "file";

    private String readPath = "E:" + File.separator + "其他";

    String copyFilePath = "E:" + File.separator + "test" + File.separator + "file" + File.separator + "cat_copy_server.jpg";



    @PostMapping(value = "/uploadFile")
    @ResponseBody
    public ResponseEntity<JSON> uploadFile(HttpServletRequest request,
                                           HttpServletResponse response,
                                           @RequestParam(value = "file") MultipartFile multipartFile) {

        try {

            FileUtils.forceMkdir(new File(directoryPath));

            String writeFilePath = directoryPath + File.separator + multipartFile.getOriginalFilename();
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(writeFilePath));

            ResultDTO resultDTO = new ResultDTO<>(true, "请求成功", Lists.newArrayList());

            return ResponseEntity.ok().body((JSON) JSON.toJSON(resultDTO));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }


    @GetMapping(value = "/getImage")
    public ResponseEntity<JSON> getImage(HttpServletRequest request, HttpServletResponse response) {

        try {

            byte[] bytes = FileUtils.readFileToByteArray(new File(readPath +  File.separator + "cat.jpg"));
            //ByteArrayInputStream 是内存读写流，不同于指向硬盘的流,不需要手动关闭
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

            response.setContentType(MediaType.IMAGE_JPEG_VALUE); //一定要放在copy前面写
            IOUtils.copy(inputStream,response.getOutputStream());



            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }

    @GetMapping(value = "/getImage2")
    public ResponseEntity<JSON> getImage2(HttpServletRequest request, HttpServletResponse response) {

        InputStream inputStream = null;
        try {

            String logoUrl = "http://localhost:8080/mvc/getImage";
            URL url = new URL(logoUrl);

            FileUtils.copyURLToFile(url,new File(copyFilePath));

            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(inputStream = url.openStream(), response.getOutputStream());

            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


    @GetMapping(value = "/downloadImage")
    public ResponseEntity<JSON> downloadImage(HttpServletRequest request, HttpServletResponse response) {

        try {

            byte[] bytes = FileUtils.readFileToByteArray(new File(readPath +  File.separator + "cat.jpg"));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);


            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE); //下载
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("猫.jpg", "utf-8"));
            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            response.setHeader("X-Frame-Options", "SAMEORIGIN");


            IOUtils.copy(inputStream,response.getOutputStream());

            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }


    @GetMapping(value = "/downloadImage2")
    public ResponseEntity<JSON> downloadImage2(HttpServletRequest request, HttpServletResponse response) {

        InputStream inputStream = null;
        try {

            String logoUrl = "http://localhost:8080/mvc/getImage";
            URL url = new URL(logoUrl);

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE); //下载
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("猫.jpg", StandardCharsets.UTF_8.name()));
            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            response.setHeader("X-Frame-Options", "SAMEORIGIN");


            IOUtils.copy(inputStream = url.openStream(), response.getOutputStream());

            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


    @GetMapping(value = "/getPdf")
    public ResponseEntity<JSON> getPdf(HttpServletRequest request, HttpServletResponse response) {

        try {

            byte[] bytes = FileUtils.readFileToByteArray(new File(readPath +  File.separator + "董宇简历.pdf"));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            IOUtils.copy(inputStream,response.getOutputStream());

            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }

    @GetMapping(value = "/getPdf2")
    public ResponseEntity<JSON> getPdf2(HttpServletRequest request, HttpServletResponse response) {

        InputStream inputStream = null;
        try {

            String logoUrl = "http://localhost:8080/mvc/getPdf";
            URL url = new URL(logoUrl);

            FileUtils.copyURLToFile(url,new File(copyFilePath));

            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            IOUtils.copy(inputStream = url.openStream(), response.getOutputStream());

            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


    @GetMapping(value = "/downloadPdf")
    public ResponseEntity<JSON> downloadPdf(HttpServletRequest request, HttpServletResponse response) {

        InputStream inputStream = null;
        try {

            String logoUrl = "http://localhost:8080/mvc/getPdf";
            logoUrl = "http://10.161.17.105:8082/ifs-outer/EUEBAMACDOHSDSCDHPDRAWDNBIFJICJCHABBGHAAJOIPEBGZBMAPJCCQFWFAGRINEMEBGLHEAXAKDSGIEBDPBZJQHVIXHODAJIDUBZFLBKCTHBBREEIKHDBZIBDOGWEAFM.h5f";
            URL url = new URL(logoUrl);


            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE); //下载
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("测试.pdf", "utf-8"));
            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            response.setHeader("X-Frame-Options", "SAMEORIGIN");


            IOUtils.copy(inputStream = url.openStream(), response.getOutputStream());

            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


    @GetMapping(value = "/downloadExcel")
    public ResponseEntity<JSON> downloadExcel(HttpServletRequest request, HttpServletResponse response) {

        try {

            byte[] bytes = FileUtils.readFileToByteArray(new File(readPath +  File.separator + "issue_import.xlsx"));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);


            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE); //下载
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode("测试_20190925.xlsx", "utf-8"));
            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            response.setHeader("X-Frame-Options", "SAMEORIGIN");


            IOUtils.copy(inputStream,response.getOutputStream());

            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }
}
