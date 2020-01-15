package com.kstarrain.controller;

import com.kstarrain.exception.BusinessException;
import com.kstarrain.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author: DongYu
 * @create: 2019-02-17 13:19
 * @description:
 */
@Slf4j
@Controller
public class GoodsController{

    @Autowired
    private IGoodsService goodsService;


    public void reduceStockById(String buyerId, String goodsId, int quantity){

        try {
            goodsService.reduceStockById(buyerId,goodsId,quantity);
        } catch (BusinessException e) {
            System.out.println(buyerId + " ---> " + e.getMessage());
        } catch (Exception e) {
            System.out.println(buyerId + " ---> " + "系统异常");
            log.error(e.getMessage(),e);
        }

    }

}
