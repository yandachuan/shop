package com.neuedu.service.impl;

import com.neuedu.entity.GoodsTag;
import com.neuedu.mapper.GoodsTagMapper;
import com.neuedu.service.GoodsTagService;
import com.neuedu.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsTagImpl implements GoodsTagService {

    @Autowired
    private GoodsTagMapper goodsTagMapper;

    //插入商品规格
    @Override
    public boolean insertGoodsTag(GoodsTag goodsTag) {
        goodsTag.setId(SnowFlake.nextId());
        return goodsTagMapper.insertSelective(goodsTag) > 0;
    }

    //删除商品规格
    @Override
    public boolean deleteGoodsTag(Long goodsId) {
        return goodsTagMapper.deleteByGoodsId(goodsId) > 0;
    }

    //查找商品规格
    @Override
    public List<GoodsTag> findGoodsTag(Long goodsId) {
        return goodsTagMapper.findByGoodsId(goodsId);
    }

}
