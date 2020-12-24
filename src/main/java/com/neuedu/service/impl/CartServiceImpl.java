package com.neuedu.service.impl;


import com.neuedu.entity.Cart;
import com.neuedu.entity.Goods;
import com.neuedu.mapper.CartMapper;
import com.neuedu.mapper.GoodsMapper;
import com.neuedu.service.CartService;
import com.neuedu.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private GoodsMapper goodsMapper;

  @Override
  public int getCartQuantity(Long userid) {
      return cartMapper.findCartQuantity(userid);
  }

    @Override
    public boolean addCart(Long userid,Long goodsid, Integer quantity) {

        //判断用户是否购买过商品
        Cart cart = cartMapper.findCart(userid, goodsid);

        if(cart==null){   //未购买，添加购物项

            cart = new Cart();
            cart.setUserid(userid);
            cart.setGoodsid(goodsid);
            cart.setQuantity(quantity);
            cart.setIschecked(1);
            return cartMapper.insert(cart) > 0;

        }else{   //已购买，修改购物项，增加数量
            cart.setQuantity(cart.getQuantity() + quantity);
            return cartMapper.updateByPrimaryKey(cart)>0;
        }
    }

    @Override
    public List<CartVO> getCartVOList(Long userid) {

        List<Cart> cartList = cartMapper.findCartList(userid);

        List<CartVO> cartVOList = new ArrayList<>();

        for (Cart cart : cartList) {

            //创建CartVO对象
            CartVO cartVO = new CartVO();

            //填充CartVO对象
            BeanUtils.copyProperties(cart, cartVO);

            //填充商品的名称，价格，图片，库存
            Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsid());

            cartVO.setPrice(goods.getPrice());
            cartVO.setGoodsname(goods.getGoodsname());
            cartVO.setImag0(goods.getImag0());
            cartVOList.add(cartVO);
        }

        return cartVOList;
    }

    @Override
    public List<CartVO> getCheckedVOList(Long userid) {

        List<Cart> cartList = cartMapper.findCheckedList(userid);

        List<CartVO> cartVOList = new ArrayList<>();

        for (Cart cart : cartList) {

            //创建CartVO对象
            CartVO cartVO = new CartVO();

            //填充CartVO对象
            BeanUtils.copyProperties(cart, cartVO);

            //填充商品的名称，价格，图片，库存
            Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsid());

            cartVO.setPrice(goods.getPrice());
            cartVO.setGoodsname(goods.getGoodsname());
            cartVO.setImag0(goods.getImag0());
            cartVOList.add(cartVO);
        }
        return cartVOList;
    }

    @Override
    public boolean deleteCart(Long cartid) {
        return cartMapper.deleteByPrimaryKey(cartid)>0;
    }

    @Override
    public boolean deleteCartBatch(Long[] cartids) {
        return cartMapper.deleteCartBatch(cartids);
    }

    @Override
    public boolean clearCart(Long userid) {
        return cartMapper.clearCart(userid);
    }

    @Override
    public boolean deleteCheckedCart(Long userid) {
        return cartMapper.deleteCheckedCart(userid);
    }

    @Override
    public boolean updateCart(Long cartid, int operationType) {
        return cartMapper.updateCart(cartid, operationType);
    }

    @Override
    public boolean checkAll(Long userid, boolean checkAllFlag) {
        return cartMapper.checkAll(userid, checkAllFlag);
    }
}
