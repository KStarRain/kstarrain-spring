package com.kstarrain.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.exception.CommonErrorCode;
import com.kstarrain.vo.request.TestRequest;
import com.kstarrain.service.ITestService;
import com.kstarrain.response.ResultDTO;
import com.kstarrain.vo.response.TestVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @RequestMapping注解中
 *    consumes属性：request的headers中Content-Type必须匹配，否则无法执行
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

    @Autowired
    private ITestService testService;


    /**
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "/test1",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String test1(HttpServletRequest request) {
        try {
            Map data = testService.test1(request);

            JSONObject resultDTO = new JSONObject();
            resultDTO.put("data",data);
            resultDTO.put("message","/test1[GET] json 请求成功");
            return resultDTO.toString();
        } catch (BusinessException e) {
            return e.toJSON().toString();
        } catch (Exception e) {
            return CommonErrorCode.SYSTEM_ERROR.toJSON().toString();
        }
    }


    /**
     * @param request
     * @param response
     * @param testRequest
     * @return ResultDTO<TestVO>
     */
    @RequestMapping(value = "/test2",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResultDTO<TestVO> test2(HttpServletRequest request,@RequestBody TestRequest testRequest) {

        ResultDTO<TestVO> result = new ResultDTO<>();
        try {
            TestVO data = testService.test2(testRequest);
            result.setData(data);
            result.setMessage("/test2[POST] json 请求成功");
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(CommonErrorCode.SYSTEM_ERROR.getCode());
            result.setMessage(CommonErrorCode.SYSTEM_ERROR.getMessage());
        }
        return result;
    }


    /**
     * @param request
     * @param testRequest
     * @return ResponseEntity<JSON>
     */
    @RequestMapping(value = "/test3",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
    public ResponseEntity<JSON> test3(HttpServletRequest request, @RequestBody TestRequest testRequest
            , @CookieValue(value = "userId",required = false) String userId) {

        try {
            TestVO data = testService.test2(testRequest);

            JSONObject resultDTO = new JSONObject();
            resultDTO.put("data",data);
            resultDTO.put("cookie","userId="+userId);
            resultDTO.put("pageNum",testRequest.getPageInfo().getPageNum());
            resultDTO.put("pageSize",testRequest.getPageInfo().getPageSize());
            resultDTO.put("message","/test3[POST] json 请求成功");
            return ResponseEntity.ok().body(resultDTO);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }


    /**
     * @param request
     * @param testRequest
     * @param bindingResult
     * @return ResponseEntity<JSON>
     */
    @RequestMapping(value = "/test4",method = RequestMethod.POST, produces = "application/json;text/plain;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<JSON> test4(HttpServletRequest request,@RequestBody @Valid TestRequest testRequest, BindingResult bindingResult) {

        try {
            if (bindingResult != null && bindingResult.hasErrors()) {
                throw new BusinessException("param error",bindingResult.getFieldErrors().get(0).getDefaultMessage());
            }
            TestVO data = testService.test2(testRequest);

            JSONObject resultDTO = new JSONObject();
            resultDTO.put("data",data);
            resultDTO.put("pageNum",testRequest.getPageInfo().getPageNum());
            resultDTO.put("pageSize",testRequest.getPageInfo().getPageSize());
            resultDTO.put("message","/test4[POST] @Valid json 请求成功");
            return ResponseEntity.ok().body(resultDTO);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }


    /**
     * 表单 单个参数接收
     * @param name
     * @return
     */
    @RequestMapping(value = "/test5",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JSON> test5(@RequestParam("name")String name,@RequestParam("age")String age) {

        try {
            if (StringUtils.isBlank(name)||!NumberUtils.isCreatable(age)){
                throw new BusinessException("param error","传参错误");
            }
            JSONObject resultDTO = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("name",name);
            data.put("age",age);
            resultDTO.put("data",data);
            resultDTO.put("message","/test5[POST] form 请求成功");
            return ResponseEntity.ok().body(resultDTO);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }


    /**
     * 表单 实体类接收
     * @param name
     * @return
     */
    @RequestMapping(value = "/test6",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JSON> test6(TestRequest testRequest) {

        try {
            if (StringUtils.isBlank(testRequest.getName()) || testRequest.getAge() == null || testRequest.getAge() < 1){
                throw new BusinessException("param error","传参错误");
            }
            JSONObject resultDTO = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("name",testRequest.getName());
            data.put("age",testRequest.getAge());
            resultDTO.put("data",data);
            resultDTO.put("message","/test6[POST] form 请求成功");
            return ResponseEntity.ok().body(resultDTO);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.toJSON());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(CommonErrorCode.SYSTEM_ERROR.toJSON());
        }
    }
}
