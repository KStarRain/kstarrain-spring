package com.kstarrain.mapper;

import com.kstarrain.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: DongYu
 * @create: 2019-01-14 17:35
 * @description:
 */
public interface GoodsMapper {

    Goods findGoodsById(@Param("goodsId")String goodsId);

    int reduceStockById(@Param("goodsId")String goodsId, @Param("quantity")int quantity, @Param("version")int version);






}
