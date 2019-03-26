package com.kstarrain.service.impl;

import com.kstarrain.exception.BusinessException;
import com.kstarrain.request.TestRequest;
import com.kstarrain.service.ITestService;
import com.kstarrain.vo.TestVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class TestServiceImpl implements ITestService {


    public Map test1(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            throw new BusinessException("00001","token 无效");
        }

        Map<Object, Object> result = new HashMap<>();
        result.put("token",token);
        return result;
    }

    @Override
    public TestVO test2(TestRequest testRequest) {

        if (testRequest.getBirthday() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(testRequest.getBirthday());
            System.out.println(time);
        }

        TestVO testVO = new TestVO();
        testVO.setName(testRequest.getName());
        testVO.setAge(testRequest.getAge());
        testVO.setMoney(testRequest.getMoney());
        testVO.setBirthday(testRequest.getBirthday());
        testVO.setInterest(testRequest.getInterest());
        return testVO;
    }

}
