package com.kstarrain.service.impl;

import com.kstarrain.mapper.OrderMapper;
import com.kstarrain.pojo.Order;
import com.kstarrain.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author: DongYu
 * @create: 2019-02-17 12:55
 * @description: 订单service
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public void createOrder(String buyerId, String goodsId, int goodsNum) {

        Order order = new Order();
        order.setGoodsId(goodsId);
        order.setGoodsNum(goodsNum);
        order.setBuyerId(buyerId);
        order.setTradeStatus(1);
        order.setCreateDate(new Date());
        order.setUpdateDate(new Date());
        order.setAliveFlag("1");

        orderMapper.insertOrder(order);
    }
}
