package com.neuedu.mapper;

import com.neuedu.entity.Goods;
import com.neuedu.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long goodsid);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);


    List<Goods> findRecommend(Long[] goodsId);//推荐商品

    List<Goods> findSellMore();//热卖商品

    int OffGoods(Long goodsid);//下架商品

    List<Goods> findByTypeOne(int firstTypeId);//按一级类型查

    List<Goods> findByTypeTwo(int secondTypeId);//按二级类型查

    List<Goods> findByName(String goodsName);//通过名字模糊查询

    List<Goods> findGoods(@Param("firstTypeId") Long firstTypeId,
                          @Param("secondTypeId") Long secondTypeId,
                          @Param("goodsName") String goodsName,
                          @Param("minPrice") Double minPrice,
                          @Param("maxPrice") Double maxPrice,
                          @Param("storeId") Long storeId);//用户通过各种条件查询

    List<Goods> findGoodsBack(@Param("firstTypeId") Long firstTypeId,
                              @Param("secondTypeId") Long secondTypeId,
                              @Param("goodsName") String goodsName,
                              @Param("minPrice") Double minPrice,
                              @Param("maxPrice") Double maxPrice,
                              @Param("storeId") Long storeId);//管理员通过各种条件查询

    List<Goods> findByStoreId(Long storeId);//通过店铺id进行查询

}