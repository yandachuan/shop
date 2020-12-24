package com.neuedu.controller;

import com.github.pagehelper.PageInfo;
import com.neuedu.common.ServerResponse;
import com.neuedu.entity.FirstType;
import com.neuedu.entity.Goods;
import com.neuedu.entity.SecondType;
import com.neuedu.service.GoodsService;
import com.neuedu.vo.GoodsVo;
import com.neuedu.vo.SecondTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    //添加商品
    @RequestMapping(value = "/addGoods")
    public ServerResponse addGoods(Goods goods,
                                   @RequestParam(required = false, value = "pictureFile") MultipartFile[] pictureFiles,
                                   @RequestParam(required = false, defaultValue = "0")String store) throws IOException {

        if (goodsService.insertGoods(goods, pictureFiles,store)) {
            return ServerResponse.success("添加成功！");
        } else {
            return ServerResponse.error("添加失败！");
        }

    }

    //修改商品
    @RequestMapping(value = "/updateGoods")
    public ServerResponse updateGoods(Goods goods) {

        if (goodsService.updateGoods(goods)) {
            return ServerResponse.success("修改成功！");
        } else {
            return ServerResponse.error("修改失败！");
        }

    }

    //商品下架
    @RequestMapping(value = "/OffGoods")
    public ServerResponse OffGoods(String goodsid) {

        Long id = Long.parseLong(goodsid);

        if (goodsService.OffGoods(id)) {
            return ServerResponse.success("下架成功！");
        } else {
            return ServerResponse.error("下架失败！");
        }

    }


    //商品详情
    @RequestMapping(value = "/findDetailById/{goodsid}")
    public ServerResponse findDetailById(@PathVariable("goodsid")Long goodsid) {

        GoodsVo goodsVo = goodsService.findDetailById(goodsid);

        if (goodsVo != null) {
            return ServerResponse.success("查询成功！").data("goodsVo", goodsVo);
        } else {
            return ServerResponse.error("查询失败！");
        }

    }

    //热卖商品
    @RequestMapping(value = "/findSellMore")
    public ServerResponse findSellMore() {

        List<Goods> goodsList = goodsService.findSellMore();

        if (goodsList.size() > 0) {
            return ServerResponse.success("查询成功！").data("goodsList", goodsList);
        } else {
            return ServerResponse.error("查询失败！");
        }

    }

    //推荐（好评）商品
    @RequestMapping(value = "/findRecommendGoods")
    public ServerResponse findRecommendGoods() {

        List<Goods> goodsList = goodsService.findRecommendGoods();

        if (goodsList.size() > 0) {
            return ServerResponse.success("查询成功！").data("goodsList", goodsList);
        } else {
            return ServerResponse.error("查询失败！");
        }

    }

    @Value("${pageSize}")
    private int pageSize;

    //用户查询  查询出来的都是status=1的
    //1 按商品的两种级别类型展示(status) 2 按商品的名称关键字展示 3 按价格区间进行展示 4 店铺id
    @RequestMapping(value = "/findGoods")
    public ServerResponse findGoods(@RequestParam(required = false, defaultValue = "0") Long firsttypeid,
                                    @RequestParam(required = false, defaultValue = "0") Long secondyypeid,
                                    @RequestParam(required = false, defaultValue = "") String goodsname,
                                    @RequestParam(required = false, defaultValue = "0") Double minPrice,
                                    @RequestParam(required = false, defaultValue = "0") Double maxPrice,
                                    @RequestParam(required = false, defaultValue = "1") int pageNum,
                                    @RequestParam(required = false, defaultValue = "0") Long storeid) {

        //分页
        PageInfo<GoodsVo> goodsPageInfo = goodsService.findGoods(firsttypeid,
                secondyypeid, goodsname, minPrice, maxPrice, pageNum, pageSize, storeid);
        if (goodsPageInfo.getList().size() > 0) {
            return ServerResponse.success("查询成功！").data("goodsPageInfo", goodsPageInfo);
        } else {
            return ServerResponse.error("查询失败！");
        }

    }

    //管理员查询  查询出来的都是status不限制
    //1 按商品的两种级别类型展示(status) 2 按商品的名称关键字展示 3 按价格区间进行展示 4 店铺id
    @RequestMapping(value = "/findGoodsBack")
    public ServerResponse findGoodsBack(@RequestParam(required = false, defaultValue = "0") Long firsttypeid,
                                        @RequestParam(required = false, defaultValue = "0") Long secondyypeid,
                                        @RequestParam(required = false, defaultValue = "") String goodsname,
                                        @RequestParam(required = false, defaultValue = "0") Double minPrice,
                                        @RequestParam(required = false, defaultValue = "0") Double maxPrice,
                                        @RequestParam(required = false, defaultValue = "1") int pageNum,
                                        @RequestParam(required = false, defaultValue = "XX") String store) {

        //分页
        PageInfo<GoodsVo> goodsPageInfo = goodsService.findGoodsBack(firsttypeid,
                secondyypeid, goodsname, minPrice, maxPrice, pageNum, pageSize, store);

        if (goodsPageInfo.getList().size() > 0) {
            return ServerResponse.success("查询成功！").data("goodsPageInfo", goodsPageInfo);
        } else {
            return ServerResponse.error("查询失败！");
        }

    }

    //按店铺id获取所有商品（包括status不为1的）
    @RequestMapping(value = "/findByStoreId")
    public ServerResponse findByStoreId(Long storeid) {

        List<Goods> goodsList = goodsService.findByStoreId(storeid);

        if (goodsList.size() > 0) {
            return ServerResponse.success("查询成功！").data("goodsList", goodsList);
        } else {
            return ServerResponse.error("查询失败！");
        }

    }

    //展示所有一级分类
    @RequestMapping(value = "/findFirstType")
    public ServerResponse findFirstType() {

        List<FirstType> firstTypeList = goodsService.findAllFirstType();

        if (firstTypeList.size() > 0) {
            return ServerResponse.success("查询成功！").data("firstTypeList", firstTypeList);
        } else {
            return ServerResponse.error("查询失败！");
        }

    }

    //展示所有二级分类
    @RequestMapping(value = "/findSecondType")
    public ServerResponse findSecondType( @RequestParam(required = false, defaultValue = "1")int currentPage) {

        PageInfo<SecondTypeVo> secondTypeList = goodsService.findAllSecondType(currentPage,pageSize);

        if (secondTypeList.getList().size() > 0) {
            return ServerResponse.success("查询成功！").data("secondTypeList", secondTypeList);
        } else {
            return ServerResponse.error("查询失败！");
        }
    }

    //根据一级分类来展示二级分类
    @RequestMapping(value = "/findSecondTypeByFirstId")
    public ServerResponse findSecondTypeByFirstId(Long firsttypeid) {

        List<SecondType> secondTypeList = goodsService.findAllSecondTypeByFirstId(firsttypeid);

        if (secondTypeList.size() > 0) {
            return ServerResponse.success("查询成功！").data("secondTypeList", secondTypeList);
        } else {
            return ServerResponse.error("查询失败！");
        }

    }

    //添加一级类别
    @RequestMapping(value = "/addFirstType")
    public ServerResponse addFirstType(String firsttypeName) {

        if (goodsService.addFirstType(firsttypeName)) {
            return ServerResponse.success("添加成功！");
        } else {
            return ServerResponse.error("添加失败！");
        }

    }

    //添加二级类别
    @RequestMapping(value = "/addSecondType")
    public ServerResponse addSecondType(String secondtypeName,Long firsttypeid) {

        if (goodsService.addSecondType(secondtypeName,firsttypeid)) {
            return ServerResponse.success("添加成功！");
        } else {
            return ServerResponse.error("添加失败！");
        }

    }

    //删除第二级别分类
    @RequestMapping(value = "/deleteSecondType")
    public ServerResponse deleteSecondType(Long secondtypeid) {

        if (goodsService.deleteSecondType(secondtypeid)) {
            return ServerResponse.success("删除成功！");
        } else {
            return ServerResponse.error("删除失败！");
        }

    }

    //修改二级分类
    @RequestMapping(value = "/upadateSecondType")
    public ServerResponse upadateSecondType(Long secondtypeid,String secondtypeName) {

        if (goodsService.upadateSecondType(secondtypeid,secondtypeName)) {
            return ServerResponse.success("修改成功！");
        } else {
            return ServerResponse.error("修改失败！");
        }

    }

}
