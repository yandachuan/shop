package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.entity.User;
import com.neuedu.service.CartService;
import com.neuedu.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/add")
    public ServerResponse add(Long goodsid, Integer quantity, HttpSession session) {

        if (session.getAttribute("user") != null) {   //用户已登录  转移到拦截器中

            //从session中取出当前登录的用户
            User user = (User) session.getAttribute("user");

            if (cartService.addCart(user.getUserid(), goodsid, quantity)) {

                //添加成功后，获取购物车中最新的商品种类数
                int cartQuantity = cartService.getCartQuantity(user.getUserid());

                return ServerResponse.success("购物车添加成功").data("cartQuantity", cartQuantity);   //需要返回cartQuantity
            } else {
                return ServerResponse.error("购物车添加失败");
            }
        } else {
            return ServerResponse.error("用户未登录");
        }
    }

    @RequestMapping("/list")
    public ServerResponse list(HttpSession session) {

        if (session.getAttribute("user") != null) {   //用户已登录  转移到拦截器中

            //从session中取出当前登录的用户
            User user = (User) session.getAttribute("user");

            List<CartVO> cartVOList = cartService.getCartVOList(user.getUserid());

            if(cartVOList.size()>0){
                return ServerResponse.success("获取购物车列表成功").data("cartVOList", cartVOList);
            }else{
                return ServerResponse.error("购物车空空如也");
            }

        } else {
            return ServerResponse.error("用户未登录");
        }
    }

    // 获取当前用户已选择的购物车列表
    @RequestMapping("/getCheckedList")
    public ServerResponse getCheckedCartVOList(HttpSession session){
        if (session.getAttribute("user") != null) {   //用户已登录  转移到拦截器中

            //从session中取出当前登录的用户
            User user = (User) session.getAttribute("user");

            List<CartVO> cartVOList = cartService.getCheckedVOList(user.getUserid());

            if(cartVOList.size()>0){
                return ServerResponse.success("获取购物车列表成功").data("cartVOList", cartVOList);
            }else{
                return ServerResponse.error("购物车空空如也");
            }

        } else {
            return ServerResponse.error("用户未登录");
        }
    }


    @RequestMapping("/delete/{cartid}")
    public ServerResponse delete(@PathVariable("cartid")Long cartid, HttpSession session) {
        if(cartService.deleteCart(cartid)){
            return ServerResponse.success("删除购物车商品成功");  //不需要返回cartQuantity，前台直接减1
        }else{
            return ServerResponse.error("删除购物车商品失败");
        }
    }

    @RequestMapping("/deleteBatch")
    public ServerResponse deleteBatch(Long[] cartids, HttpSession session) {
        if(cartService.deleteCartBatch(cartids)){

            //从session中取出当前登录的用户
            User user = (User) session.getAttribute("user");

            //删除成功后，获取购物车中最新的商品种类数
            int cartQuantity = cartService.getCartQuantity(user.getUserid());

            return ServerResponse.success("批量删除购物车商品成功").data("cartQuantity", cartQuantity);  //需要返回cartQuantity
        }else{
            return ServerResponse.error("批量删除购物车商品失败");
        }
    }

    @RequestMapping("/clear")
    public ServerResponse clear(HttpSession session) {

        //从session中取出当前登录的用户
        User user = (User) session.getAttribute("user");

        if(cartService.clearCart(user.getUserid())){
            return ServerResponse.success("清空购物车成功");  //不需要返回cartQuantity，前台直接清零
        }else{
            return ServerResponse.error("清空购物车失败");
        }
    }

    @RequestMapping("/update")
    public ServerResponse update(Long cartid, int operationType, HttpSession session) {

        if(cartService.updateCart(cartid, operationType)){
            return ServerResponse.success("修改购物车商品成功");
        }else{
            return ServerResponse.error("修改购物车商品失败");
        }
    }

    @RequestMapping("/checkAll")
    public ServerResponse checkAll(boolean checkAllFlag, HttpSession session) {

        //从session中取出当前登录的用户
        User user = (User) session.getAttribute("user");

        if(cartService.checkAll(user.getUserid(), checkAllFlag)){
            return ServerResponse.success("购物车商品全选成功");
        }else{
            return ServerResponse.error("购物车商品全选失败");
        }
    }
}
