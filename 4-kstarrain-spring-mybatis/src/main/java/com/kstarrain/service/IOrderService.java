package com.kstarrain.service;


/**
 * @author: DongYu
 * @create: 2019-02-17 12:37
 * @description:
 */
public interface IOrderService {

    /**
     * 生成订单
     * @param buyerId  买家id
     * @param goodsId  商品id
     * @param goodsNum 商品数量
     */
    void createOrder(String buyerId, String goodsId, int goodsNum);
}
