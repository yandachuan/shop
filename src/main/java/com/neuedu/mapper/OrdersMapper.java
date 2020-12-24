package com.neuedu.mapper;

import com.neuedu.entity.Orders;

import java.util.List;

public interface OrdersMapper {
    int deleteByPrimaryKey(Long orderid);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Long orderid);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);






    List<Orders> findOrderList(Long userid);

}