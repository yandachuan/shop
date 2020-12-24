package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.entity.Goods;
import com.neuedu.entity.GoodsTag;
import com.neuedu.mapper.GoodsTagMapper;
import com.neuedu.service.GoodsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/goodsTag")
public class GoodsTagController {

    @Autowired
    private GoodsTagService goodsTagService;

    //查询商品规格
    @RequestMapping(value = "/findGoodsTag")
    public ServerResponse findGoodsTag(Long goodsid) {

        List<GoodsTag> goodsTagList = goodsTagService.findGoodsTag(goodsid);

        if (goodsTagList.size()>0){
            return ServerResponse.success("查询成功！").data("goodsTagList",goodsTagList);
        }else {
            return ServerResponse.error("查询失败！");
        }

    }

    //删除商品规格
    @RequestMapping(value = "/deleteGoodsTag")
    public ServerResponse deleteGoodsTag(Long goodsid) {
        if (goodsTagService.deleteGoodsTag(goodsid)){
            return ServerResponse.success("删除成功！");
        }else {
            return ServerResponse.error("删除失败！");
        }

    }

    //插入商品规格
    @RequestMapping(value = "/insertGoodsTag")
    public ServerResponse insertGoodsTag(GoodsTag goodsTag) {
        if (goodsTagService.insertGoodsTag(goodsTag)){
            return ServerResponse.success("插入成功！");
        }else {
            return ServerResponse.error("插入失败！");
        }

    }

}
