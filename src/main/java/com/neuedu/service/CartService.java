package com.neuedu.service;


import com.neuedu.vo.CartVO;

import java.util.List;

public interface CartService {

    //获取当前用户购物车中的商品种类数
    int getCartQuantity(Long userid);

    //将商品加入到当前用户的购物车中
    boolean addCart(Long userid,Long goodsid, Integer quantity);

    //获取当前用户购物车的VO列表
    List<CartVO> getCartVOList(Long userid);

    // 获取当前用户已选择的VO列表
    List<CartVO> getCheckedVOList(Long userid);

    //删除购物车中的单个商品
    boolean deleteCart( Long cartid);

    //删除购物车中的多个商品
    boolean deleteCartBatch( Long[] cartids);

    //清空购物车
    boolean clearCart(Long userid);

    //删除选中的商品（下单时使用）
    boolean deleteCheckedCart(Long userid);

    //修改商品信息  operationType=1  加1  operationType=2  减1  operationType=3 选择/取消选择
    boolean updateCart(Long cartid, int operationType);

    //购物车商品全选/取消全选
    boolean checkAll(Long userid, boolean checkAllFlag);


}
