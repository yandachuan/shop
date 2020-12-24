package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.entity.User;
import com.neuedu.entity.UserGoods;
import com.neuedu.entity.UserStore;
import com.neuedu.mapper.UserMapper;
import com.neuedu.service.GoodsService;
import com.neuedu.service.UserGoodsService;
import com.neuedu.service.UserService;
import com.neuedu.service.UserStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import java.util.regex.Matcher;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserGoodsService userGoodsService;

    @Autowired
    private UserStoreService userStoreService;

    @RequestMapping("/login")
    public ServerResponse login(String username, String password, HttpSession session){

        //根据email登录
        if(username.contains("@")){
            User user = userService.findUserByUserEmail(username, password);
            if(user!=null){
                session.setAttribute("user",user);
                return ServerResponse.success("根据email查询user成功").data("user",user);
            }
            return ServerResponse.error("当前用户名不存在");
        }
        System.out.println(regexPhone(username));

        //根据手机号登录
        if(regexPhone(username)){
            User user = userService.findUserByLoginphone(username, password);
            if(user!=null){
                session.setAttribute("user",user);
                return ServerResponse.success("根据phone查询user成功").data("user",user);
            }
            return ServerResponse.error("当前用户名不存在");
        }

        //根据用户Id登录
        if(true){
            Long userid = Long.valueOf(username);
            User user = userService.findUserByUserId(userid, password);
            if(user!=null){
                session.setAttribute("user",user);
                return ServerResponse.success("根据userId查询user成功").data("user",user);
            }
            return ServerResponse.error("当前用户名不存在");
        }
        return ServerResponse.error("当前用户名不存在");
    }

    //用户注册
    @RequestMapping("/reg")
    public ServerResponse reg(User user,String valcode,HttpSession session){
        System.out.println("valcode-->"+valcode);
        String valcodeAnswer = (String) session.getAttribute("valcodeAnswer");
        System.out.println("valcodeAnswer-->"+valcodeAnswer);
        if(!valcodeAnswer.equals(valcode)){
            return ServerResponse.error("验证码输入错误!");
        }
        //数据校验
        if(StringUtils.isEmpty(user.getEmail())){
            return ServerResponse.error("邮箱不能为空!");
        }

        if(StringUtils.isEmpty(user.getLoginphone())){
            return ServerResponse.error("手机号不能为空!");
        }

        //数据校验(添加工具类,校验手机号是否正确)
        if(StringUtils.isEmpty(user.getPassword())){
            return ServerResponse.error("密码不能为空!");
        }

        //数据校验(根据手机号查询用户是否存在,可以前端校验)
        if(userService.findUserByphone(user.getLoginphone())){
            return ServerResponse.error("注册失败,用户名已存在!");
        }

        user.setCreatetime(new Date());
        if(userService.reg(user)){
            return ServerResponse.success("注册成功").data("user", user);
        }else {
            return ServerResponse.error("注册失败");
        }

    }

    //用户注销
    @RequestMapping("/logout")
    public ServerResponse logout(HttpSession session){
        User user = (User) session.getAttribute("user");
        System.out.println("正在注销"+user.getUserid());
        session.removeAttribute("user");
        return ServerResponse.success("注销成功");
    }

    //用户修改
    @RequestMapping("/update")
    public ServerResponse updateUser(User user, HttpSession session){
        User user1 = (User) session.getAttribute("user");
        user.setUserid(user1.getUserid());
        if(userService.updateUser(user)){
          return ServerResponse.success("更新数据成功").data("user", user);
        }
        return ServerResponse.error("更新失败");
    }

    //查询收藏商品列表
    @RequestMapping("/findGoodsList")
    public ServerResponse findGoodsList(HttpSession session){
        User user = (User) session.getAttribute("user");
        Long userid = user.getUserid();

        List<UserGoods> userGoods = userGoodsService.selectGoodsList(userid);
        if(userGoods.size()>0){
            return ServerResponse.success("查询成功").data("userGoods",userGoods);
        }
        return ServerResponse.error("查询失败");
    }

    //查询关注店铺列表
    @RequestMapping("/findStoreList")
    public ServerResponse findStoreList(HttpSession session){
        User user = (User) session.getAttribute("user");
        Long userid = user.getUserid();

        List<UserStore> userStores = userStoreService.selectStoreList(userid);
        if(userStores.size()>0){
            return ServerResponse.success("查询成功").data("userStores",userStores);
        }
        return ServerResponse.error("查询失败");

    }

    //收藏商品
    @RequestMapping("/collectGoods")
    public ServerResponse collectGoods(Long goodsid,HttpSession session){
        User user = (User) session.getAttribute("user");
        Long userid = user.getUserid();
        if(userGoodsService.collectGoods(goodsid, userid)){
            return ServerResponse.success("收藏商品成功").data("goodsid",goodsid);
        }
        return ServerResponse.error("收藏商品失败");
    }

    //取消收藏商品
    @RequestMapping("/cancleGoods")
    public ServerResponse cancleGoods(Long goodsid,HttpSession session){
        User user = (User) session.getAttribute("user");
        Long userid = user.getUserid();
        if(userGoodsService.cancleGoods(goodsid, userid)){
            return ServerResponse.success("取消成功").data("goodsid",goodsid);
        }
        return ServerResponse.error("取消收藏商品失败");
    }

    //收藏店铺
    @RequestMapping("/collectStore")
    public ServerResponse collectStore(Long storeid,HttpSession session){
        User user = (User) session.getAttribute("user");
        Long userid = user.getUserid();
        if(userStoreService.collectStore(storeid, userid)){
            return ServerResponse.success("收藏店铺成功").data("storeid",storeid);
        }
        return ServerResponse.error("收藏店铺失败");
    }

    //取消收藏店铺
    @RequestMapping("/cancleStore")
    public ServerResponse cancleStore(Long storeid,HttpSession session){
        User user = (User) session.getAttribute("user");
        Long userid = user.getUserid();
        if(userStoreService.cancleStore(storeid, userid)){
            return ServerResponse.success("取消成功").data("storeid",storeid);
        }
        return ServerResponse.error("取消收藏商品失败");
    }

    //查询是否关注页面
    @RequestMapping("/follow")
    public ServerResponse isFollow(Long storeid,HttpSession session){
        User user = (User) session.getAttribute("user");
        Long userid = user.getUserid();
        if(userStoreService.isFollow(storeid, userid)){
            return ServerResponse.success("当前店铺已关注");
        }
        return ServerResponse.error("当前店铺未关注");

    }

    //校验是否是手机号
    static boolean regexPhone(String phonenumber){
        String regex = "^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16([5,6])|(17[0-8])|(18[0-9]))|(19[1,8,9]))\\d{8}$";
        if(phonenumber.length() != 11){
            return false;
        }else{
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phonenumber);
            boolean isMatch = m.matches();
            if(isMatch){
                return true;
            } else {
                return false;
            }
        }
    }
}