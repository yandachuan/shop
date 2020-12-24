package com.neuedu.service;

import com.github.pagehelper.PageInfo;
import com.neuedu.entity.FirstType;
import com.neuedu.entity.Goods;
import com.neuedu.entity.SecondType;
import com.neuedu.vo.GoodsVo;
import com.neuedu.vo.SecondTypeVo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface GoodsService {

    public boolean insertGoods(Goods goods, MultipartFile[] pictureFiles, String store) throws IOException;//添加商品

    public boolean updateGoods(Goods goods);//修改商品

    public boolean OffGoods(Long goodsId); //下架商品

    GoodsVo findDetailById(Long goodsId);//商品详情

    List<Goods> findSellMore();//热卖商品

    List<Goods> findRecommendGoods();//好评（推荐）商品

    PageInfo<GoodsVo> findGoods(Long firstTypeId,
                                Long secondTypeId,
                                String goodsName,
                                Double minPrice,
                                Double maxPrice,
                                int currentPage,
                                int pageSize,
                                Long storeid);//按各种条件查询

    PageInfo<GoodsVo> findGoodsBack(Long firstTypeId,
                                    Long secondTypeId,
                                    String goodsName,
                                    Double minPrice,
                                    Double maxPrice,
                                    int currentPage,
                                    int pageSize,
                                    String store);//后台按各种条件查询

    List<Goods> findByStoreId(Long storeId);//通过店铺id查询

    List<FirstType> findAllFirstType();//查找全部一级分类

    PageInfo<SecondTypeVo>findAllSecondType(int currentPage, int pageSize);//查找全部二级分类

    List<SecondType> findAllSecondTypeByFirstId(Long firstTypeId);//通过一级分类id查找二级分类

    boolean addSecondType(String secondtypeName, Long firsttypeid);//添加二级类别
    boolean addFirstType(String firsttypeName);//添加一级类别

    boolean deleteSecondType(Long secondtypeid);//删除二级类别

    boolean upadateSecondType(Long secondtypeid, String secondtypeName);//修改二级分类


}
