package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.entity.Store;
import com.neuedu.entity.User;
import com.neuedu.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    //店铺详情
    @RequestMapping("/detail/{storeId}")
    public ServerResponse storeDetail(@PathVariable("storeId") Long storeId){

        Store store = storeService.findStore(storeId);
        if(store !=null){
            return ServerResponse.success("查询店铺详情成功").data("store", store);
        }else{
            return ServerResponse.error("查询店铺详情失败");
        }
    }

    //开店（插入一条店铺数据）
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ServerResponse addStore(Store store, HttpSession session){

        //从session中取出当前登录的用户对象
        User user = (User)session.getAttribute("user");
      /*  //填充用户编号userId
        store.setUserId(user.getUserId());*/
        //填充评论时间createTime
        store.setCreatetime(new Date());
        store.setFansnum(0);//填充店铺关注数fansNum
        store.setStorelevel(1);//填充店铺等级storeLevel
        if(store.getStorename()==null){
            return ServerResponse.error("店铺名称不能为空");
        }
        if (storeService.existStore(store.getStorename())){
            return ServerResponse.error("店铺名称已被占用，请换一个名称~");
        }
        storeService.addStore(store);
        return ServerResponse.success("开店成功").data("store", store);

    }

    //修改店铺信息
    @RequestMapping(value = "/updateStore")
    public ServerResponse updateStore(Store store){
        if(store.getStoreid()==null){
            return ServerResponse.error("店铺编号不能为空");
        }
        /*if(store.getStorename()==null){
            return ServerResponse.error("店铺名称不能为空");
        }*/
        if (storeService.existStore(store.getStorename())){
            return ServerResponse.error("店铺名称已被占用，请换一个名称~");
        }
        /*if(store.getFansnum()!=null){
            return ServerResponse.error("粉丝数不能修改！！！");
        }
        if(store.getStorelevel()!=null){
            return ServerResponse.error("店铺等级不能修改！！！");
        }*/
        storeService.updateStore(store);
            return ServerResponse.success("修改店铺信息成功");
    }

}
