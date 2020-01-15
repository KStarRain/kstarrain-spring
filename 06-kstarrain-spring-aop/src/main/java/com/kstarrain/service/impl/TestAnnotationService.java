package com.kstarrain.service.impl;

import com.kstarrain.annotation.test.TestAnnotation;
import org.springframework.stereotype.Service;

/**
 * @author: DongYu
 * @create: 2019-02-01 13:24
 * @description:
 */
@Service
public class TestAnnotationService {

    @TestAnnotation(id = 1,arrays = {"貂","蝉"})
    public void test01 () {}

    public void test02 () {}

    @TestAnnotation(id = 3,arrays = {"吕","布"})
    private void test03 () {}
}
