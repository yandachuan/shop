package com.neuedu.service;

import com.neuedu.entity.Goods;
import com.neuedu.entity.UserGoods;

import java.util.List;

public interface UserGoodsService {

    //收藏商品
    boolean collectGoods(Long goodsid, Long userid);

    //取消收藏商品
    boolean cancleGoods(Long goodsid, Long userid);

    List<UserGoods> selectGoodsList(Long userid);

}
