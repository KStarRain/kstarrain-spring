package com.kstarrain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author: DongYu
 * @create: 2019-02-19 09:02
 * @description:
 */
@Configuration
public class MultipartConfig {


    @Bean(value = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        //设定默认编码
        resolver.setDefaultEncoding("UTF-8");
        //设定文件上传的最大值为5MB，5*1024*1024
        resolver.setMaxUploadSize(5*1024*1024);
        //设定文件上传时写入内存的最大值，如果小于这个参数不会生成临时文件，默认为10240
        resolver.setMaxInMemorySize(40960);
        //延迟文件解析
        resolver.setResolveLazily(true);


        return resolver;
    }



}
