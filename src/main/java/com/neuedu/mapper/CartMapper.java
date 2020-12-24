package com.neuedu.mapper;

import com.neuedu.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Long cartid);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Long cartid);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);




    //查询用户购物车中的商品种类数
    int findCartQuantity( Long userid);

    //查询指定用户编号指定商品编号的购物项 （添加购物车时使用）
    Cart findCart(@Param("userid")  Long userid, @Param("goodsid") Long goodsid);

    //查询用户购物车列表
    List<Cart> findCartList( Long userid);

    // 查询选中的购物车列表
    List<Cart> findCheckedList( Long userid);


    //批量删除
    boolean deleteCartBatch(Long[] cartids);

    //清空购物车
    boolean clearCart( Long userid);

    //删除选中的商品（下单时使用）
    boolean deleteCheckedCart( Long userid);

    //修改商品信息  operationType=1  加1  operationType=2  减1  operationType=3 选择/取消选择
    boolean updateCart(@Param("cartid") Long cartid, @Param("operationType") int operationType);

    //购物车商品全选/取消全选
    boolean checkAll(@Param("userid") Long userid, @Param("checkAllFlag")boolean checkAllFlag);

}