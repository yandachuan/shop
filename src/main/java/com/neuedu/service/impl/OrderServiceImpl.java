package com.neuedu.service.impl;

import com.neuedu.entity.Cart;
import com.neuedu.entity.Goods;
import com.neuedu.entity.OrderGoods;
import com.neuedu.entity.Orders;
import com.neuedu.mapper.CartMapper;
import com.neuedu.mapper.GoodsMapper;
import com.neuedu.mapper.OrderGoodsMapper;
import com.neuedu.mapper.OrdersMapper;
import com.neuedu.service.OrderService;
import com.neuedu.util.SnowFlake;
import com.neuedu.vo.OrderGoodsVO;
import com.neuedu.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Override
    public Long createOrder(Long userid,Long addressid) {

        //(1)生成唯一的订单号
        Long orderid = SnowFlake.nextId();

        // (2)添加order表记录
        Orders order = new Orders();
        order.setOrderid(orderid);
        order.setUserid(userid);
        order.setAddressid(addressid);
        order.setCreatetime(new Date());
        ordersMapper.insert(order);

        //(3)依次取出购物车里用户选中的每种商品的编号和数量，写到ordergoods表中
        List<Cart> cartList = cartMapper.findCartList(userid);
        for (Cart cart : cartList) {
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setOrderid(orderid);
                orderGoods.setGoodsid(cart.getGoodsid());
                orderGoods.setNum(cart.getQuantity());
                orderGoodsMapper.insert(orderGoods);

                //(4)更新每种商品的库存(减去购买数量)和销量(加上购买数量)
                Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsid());
                goods.setInventory(goods.getInventory() - cart.getQuantity());
                goods.setMonthlysales(goods.getMonthlysales() + cart.getQuantity());
                goodsMapper.updateByPrimaryKey(goods);


        }

        //(5)删除购物车表中用户选中的购物记录
        cartMapper.deleteCheckedCart(userid);

        return orderid;
    }

    @Override
    public List<OrderVO> getOrderVOList(Long userid) {

        List<Orders> ordersList = ordersMapper.findOrderList(userid);

        List<OrderVO> orderVOList = new ArrayList();

        for (Orders orders : ordersList) {

            //创建orderVO对象
            OrderVO orderVO = new OrderVO();

            //填充orderVO对象
            BeanUtils.copyProperties(orders, orderVO);
            //填充OrderDetailVOList
            List<OrderGoods> orderGoodsList = orderGoodsMapper.findOrderGoods(orders.getOrderid());

            List<OrderGoodsVO> orderGoodsVOList = new ArrayList();

            int totalPrice = 0;

            for (OrderGoods orderGoods : orderGoodsList) {

                //创建 orderGoodsVO对象
                OrderGoodsVO orderGoodsVO = new OrderGoodsVO();

                //填充orderDetailVO对象
                BeanUtils.copyProperties(orderGoods, orderGoodsVO);

                //填充商品信息
                Goods goods = goodsMapper.selectByPrimaryKey(orderGoods.getGoodsid());
                orderGoodsVO.setGoodsname(goods.getGoodsname());
                orderGoodsVO.setPrice(goods.getPrice());
                orderGoodsVO.setImag0(goods.getImag0());
                //添加列表中
                orderGoodsVOList.add(orderGoodsVO);
                //累加总价格
                totalPrice += orderGoodsVO.getPrice() * orderGoodsVO.getNum();

            }
            //填充detailVO列表
            orderVO.setOrderGoodsVOList(orderGoodsVOList);
            //填充总价格
            orderVO.setTotalPrice((double) totalPrice);
            //添加列表中
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public boolean deleteOrder(Long orderid) {

        //取到商品信息
        List<OrderGoods> orderGoodsList = orderGoodsMapper.findOrderGoods(orderid);

        for (OrderGoods orderGoods : orderGoodsList) {
            
            Goods goods = goodsMapper.selectByPrimaryKey(orderGoods.getGoodsid());

            //更新库存(加上购买数量)和销量(减去购买数量)
            goods.setInventory(goods.getInventory() + orderGoods.getNum());
            goods.setMonthlysales(goods.getMonthlysales() - orderGoods.getNum());
            goodsMapper.updateByPrimaryKey(goods);
        }

        //删除OrderGoods表里的数据
        orderGoodsMapper.deleteOrderGoods(orderid);

        return ordersMapper.deleteByPrimaryKey(orderid) > 0;
    }
}
