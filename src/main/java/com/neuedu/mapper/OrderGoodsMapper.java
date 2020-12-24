package com.neuedu.mapper;

import com.neuedu.entity.OrderGoods;

import java.util.List;

public interface OrderGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderGoods record);

    int insertSelective(OrderGoods record);

    OrderGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderGoods record);

    int updateByPrimaryKey(OrderGoods record);


    List<OrderGoods> findOrderGoods(Long orderid);

    Boolean deleteOrderGoods(Long orderid);
}