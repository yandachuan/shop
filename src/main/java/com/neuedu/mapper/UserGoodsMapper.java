package com.neuedu.mapper;

import com.neuedu.entity.UserGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserGoods record);

    int insertSelective(UserGoods record);

    UserGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserGoods record);

    int updateByPrimaryKey(UserGoods record);

    boolean deleteRecord(@Param("goodsid") Long goodsid, @Param("userid") Long userid);

    List<UserGoods> selectGoodsList(Long userid);
}