package com.neuedu.controller;


import com.neuedu.common.ServerResponse;
import com.neuedu.entity.User;
import com.neuedu.service.OrderService;
import com.neuedu.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
   private OrderService orderService;

    @RequestMapping("/create/{addressid}")
    public ServerResponse create(HttpSession session, @PathVariable("addressid") Long addressid){

        if(session.getAttribute("user")!=null){     //用户已登录  转移到拦截器中

            //从session中取出当前登录的用户
            User user = (User)session.getAttribute("user");

            Long orderid = orderService.createOrder(user.getUserid(), addressid);

            if(orderid!=null){
                return ServerResponse.success("下单成功").data("orderid" , orderid);
            }else{
                return ServerResponse.error("下单失败");
            }

        }else{
            return ServerResponse.error("用户未登录");
        }
    }

    @RequestMapping("/list")
    public ServerResponse list(HttpSession session) {

        if(session.getAttribute("user")!=null){   //用户已登录  转移到拦截器中

            //从session中取出当前登录的用户
            User user = (User)session.getAttribute("user");

            List<OrderVO> orderVOList = orderService.getOrderVOList(user.getUserid());

            if(orderVOList.size() > 0){
                return ServerResponse.success("获取订单列表成功").data("orderVOList", orderVOList);
            }else{
                return ServerResponse.error("您没有任何订单");
            }

        }else{
            return ServerResponse.error("用户未登录");
        }
    }


    @RequestMapping("/delete/{orderid}")
    public ServerResponse delete(HttpSession session, @PathVariable("orderid")Long orderid) {

        if(session.getAttribute("user")!=null){   //用户已登录  转移到拦截器中

            boolean deleteOrder = orderService.deleteOrder(orderid);

            if(deleteOrder){
                return ServerResponse.success("取消订单成功");
            }else{
                return ServerResponse.error("取消订单失败");
            }

        }else{
            return ServerResponse.error("用户未登录");
        }
    }


}
