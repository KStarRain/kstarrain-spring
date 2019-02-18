package com.kstarrain.service.impl;

import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.mapper.GoodsMapper;
import com.kstarrain.pojo.Goods;
import com.kstarrain.service.IGoodsService;
import com.kstarrain.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author: DongYu
 * @create: 2019-02-17 12:55
 * @description: 高并发秒杀--基于数据库版本号的乐观锁
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private IOrderService orderService;


    @Override
    @Transactional
    public void reduceStockById(String buyerId, String goodsId, int quantity){

        if (quantity <= 0){throw new BusinessException(BusinessErrorCode.BUSINESS001);}

        /** 乐观锁减库存 */
        this.reduceStockById(goodsId, quantity);

        /** 创建订单 */
        orderService.createOrder(buyerId, goodsId, quantity);

    }


    private void reduceStockById(String goodsId, int quantity){

        Goods goods = goodsMapper.findGoodsById(goodsId);

        if (goods == null){throw new BusinessException(BusinessErrorCode.BUSINESS002);}

        if (goods.getStock() > 0){

            if (quantity > goods.getStock()){throw new BusinessException(BusinessErrorCode.BUSINESS003);}

            /** 乐观锁减库存 */
            int num = goodsMapper.reduceStockById(goodsId, quantity, goods.getVersion());

            if (num == 0){throw new BusinessException(BusinessErrorCode.BUSINESS004);}

        }else if (goods.getStock() == 0){
            throw new BusinessException(BusinessErrorCode.BUSINESS005);
        }else if (goods.getStock() < 0){
            throw new BusinessException(BusinessErrorCode.BUSINESS000);
        }

    }
}
