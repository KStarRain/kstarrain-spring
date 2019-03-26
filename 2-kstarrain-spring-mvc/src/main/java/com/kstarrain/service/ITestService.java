package com.kstarrain.service;

import com.kstarrain.request.TestRequest;
import com.kstarrain.vo.TestVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ITestService {

    Map test1(HttpServletRequest request);

    TestVO test2(TestRequest testRequest);
}
