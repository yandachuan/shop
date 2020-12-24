package com.neuedu.controller;


import com.neuedu.common.ServerResponse;
import com.neuedu.entity.Address;
import com.neuedu.entity.User;
import com.neuedu.service.AddressService;
import com.neuedu.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping("/get/{addressid}")
    public ServerResponse create(HttpSession session, @PathVariable("addressid") Long addressid){

        if(session.getAttribute("user")!=null){     //用户已登录  转移到拦截器中

            AddressVO addressVO = addressService.getAddress(addressid);

            if(addressVO!=null){
                return ServerResponse.success("获取地址成功").data("addressVO" , addressVO);
            }else{
                return ServerResponse.error("获取地址失败");
            }

        }else{
            return ServerResponse.error("用户未登录");
        }
    }

    @RequestMapping("/list")
    public ServerResponse list(HttpSession session) {

        if (session.getAttribute("user") != null) {   //用户已登录  转移到拦截器中

            //从session中取出当前登录的用户
            User user = (User) session.getAttribute("user");

            List<AddressVO> addressList = addressService.getAddressList(user.getUserid());

            if (addressList.size() > 0) {
                return ServerResponse.success("用户地址列表获取成功").data("addressList", addressList);
            } else {
                return ServerResponse.error("您还没有任何收货地址");
            }

        } else {
            return ServerResponse.error("用户未登录");
        }
    }

    // 添加地址接口
    @RequestMapping("/addRess")
    public ServerResponse addRess(HttpSession session, Address address){
        if(session.getAttribute("user") != null){
            User user = (User)session.getAttribute("user");
            // 从当前登录中的session中拿出userId放到address中
            address.setUserid(user.getUserid());
            Boolean aBoolean = addressService.addAddress(address);
            if(aBoolean){
                return ServerResponse.success("添加地址成功");
            }else{
                return ServerResponse.error("添加地址失败");
            }
        }else{
            return ServerResponse.error("未登录");
        }
    }


    // 删除收货地址
    @RequestMapping("/delRess")/*/{addressid}*/
    public ServerResponse delRess(HttpSession session, /*@PathVariable("addressid")*/Long addressid){

        User user = (User)session.getAttribute("user");
        if(null != user){
            Boolean aBoolean = addressService.delAddress(addressid);
            if(aBoolean){
                return ServerResponse.success("删除成功");
            }else{
                return ServerResponse.error("删除失败");
            }
        }else{
            return ServerResponse.error("未登录");
        }
    }


    // 修改收货地址
    @RequestMapping("/updateRess")
    public ServerResponse updateRess(HttpSession session,Address address){
        User user = (User)session.getAttribute("user");
        if(null != user) {
            Boolean aBoolean = addressService.updateAddress(address);
            if(aBoolean){
                return ServerResponse.success("修改成功");
            }else{
                return ServerResponse.error("修改失败");
            }
        }else{
            return ServerResponse.error("未登录");
        }
    }

}
