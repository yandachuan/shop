package com.neuedu.service.impl;

import com.neuedu.entity.Goods;
import com.neuedu.entity.UserGoods;
import com.neuedu.mapper.GoodsMapper;
import com.neuedu.mapper.UserGoodsMapper;
import com.neuedu.mapper.UserMapper;
import com.neuedu.service.UserGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserGoodsServiceImple implements UserGoodsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserGoodsMapper userGoodsMapper;

    @Override
    public boolean collectGoods(Long goodsid, Long userid) {

//        System.out.println("进入UserGoodsMapper");

        UserGoods userGoods = new UserGoods();
        Goods goods = goodsMapper.selectByPrimaryKey(userid);
        userGoods.setGoodsid(goodsid);
        userGoods.setUserid(userid);
        userGoods.setImag0(goods.getImag0());
        userGoods.setGoodsname(goods.getGoodsname());
        userGoods.setPrice(goods.getPrice());
        userGoods.setCollectionnum(goods.getCollectionnum());
        userGoods.setCreatetime(new Date());

        //修改商品表的Collectionnum
        goods.setCollectionnum(goods.getCollectionnum()+1);
        goodsMapper.updateByPrimaryKey(goods);

        return userGoodsMapper.insert(userGoods)>0;
    }

    @Override
    public boolean cancleGoods(Long goodsid, Long userid) {
        System.out.println("cancleGoods");

        Goods goods = goodsMapper.selectByPrimaryKey(userid);
        //修改商品表的Collectionnum
        goods.setCollectionnum(goods.getCollectionnum()-1);
        goodsMapper.updateByPrimaryKey(goods);
        return userGoodsMapper.deleteRecord(goodsid,userid);
    }

    @Override
    public List<UserGoods> selectGoodsList(Long userid) {
        return userGoodsMapper.selectGoodsList(userid);
    }


}
