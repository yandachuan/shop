package com.neuedu.service;

import com.neuedu.entity.GoodsTag;

import java.util.List;

public interface GoodsTagService {

    boolean insertGoodsTag(GoodsTag goodsTag);//插入商品规格

    List<GoodsTag> findGoodsTag(Long goodsTagId);//查询商品规格

    boolean deleteGoodsTag(Long goodsTagId);//删除商品规格

}
