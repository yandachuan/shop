package com.neuedu.mapper;

import com.neuedu.entity.GoodsTag;

import java.util.List;

public interface GoodsTagMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsTag record);

    int insertSelective(GoodsTag record);

    GoodsTag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsTag record);

    int updateByPrimaryKey(GoodsTag record);

    int deleteByGoodsId(Long goodsId);//通过商品id来删除商品

    List<GoodsTag> findByGoodsId(Long goodsId);//通过商品id来查找商品
}