package com.neuedu.service;


import com.neuedu.vo.OrderVO;

import java.util.List;

public interface OrderService {

    //下单 返回的是生成的订单号
    Long createOrder(Long userid, Long addressid);

    //获取订单列表
    List<OrderVO> getOrderVOList(Long userid);

    //删除订单
    boolean deleteOrder(Long orderid);
}
