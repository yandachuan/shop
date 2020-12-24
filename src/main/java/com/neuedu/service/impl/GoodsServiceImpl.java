package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neuedu.entity.FirstType;
import com.neuedu.entity.Goods;
import com.neuedu.entity.SecondType;
import com.neuedu.mapper.*;
import com.neuedu.service.GoodsService;
import com.neuedu.util.SnowFlake;
import com.neuedu.vo.GoodsVo;
import com.neuedu.vo.SecondTypeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private FirstTypeMapper firstTypeMapper;

    @Autowired
    private SecondTypeMapper secondTypeMapper;

    @Autowired
    private StoreMapper storeMapper;

    //添加商品
    @Transactional
    @Override
    public boolean insertGoods(Goods goods,
                               MultipartFile[] pictureFiles,
                               String store) throws IOException {

        goods.setStoreid(storeMapper.selectByName(store));
        goods.setMonthlysales(0);
        goods.setCollectionnum(0);

        int index = 0;
        for (MultipartFile pictureFile : pictureFiles) {
            //使用UUID给图片重命名，并去掉四个“-”
            String name = UUID.randomUUID().toString().replaceAll("-", "");

            //获取文件的扩展名
            String originalFilename = pictureFile.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (ext.equals("")) {
                break;
            }
            index++;
            //设置图片上传路径(绝对路径，要修改，前后端分离不能放webapp访问不到)
            String url = "F:\\8_month\\东软电商项目\\shop-master\\src\\main\\resources\\static\\image\\goods";

            //如果上传路径不存在，自己创建
            File dir = new File(url);
            if (!dir.exists()) {
                dir.mkdir();
            }

            //以绝对路径保存重名命后的图片
            pictureFile.transferTo(new File(url + "\\" + name + "." + ext));

            //把图片存储路径保存到数据库
            if (index == 1){
                goods.setImag0(name + "." + ext);
            }
            if (index == 2){
                goods.setImag1(name + "." + ext);
            }
            if (index == 3){
                goods.setImag2(name + "." + ext);
            }
        }

        goods.setGoodsid(SnowFlake.nextId());
        goods.setCreatetime(new Date());
        return goodsMapper.insertSelective(goods) > 0;
    }

    //修改商品
    @Override
    public boolean updateGoods(Goods goods) {
        return goodsMapper.updateByPrimaryKeySelective(goods) > 0;
    }

    //下架商品
    @Override
    public boolean OffGoods(Long goodsId) {
        return goodsMapper.OffGoods(goodsId) > 0;
    }

    //商品详情
    @Override
    public GoodsVo findDetailById(Long goodsId) {

        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goods,goodsVo);
        goodsVo.setFirsttypeName(firstTypeMapper.selectByPrimaryKey(goods.getFirsttypeid()).getFirsttypename());
        goodsVo.setSecondtypeName(secondTypeMapper.selectByPrimaryKey(goods.getSecondtypeid()).getSecondtypename());
        goodsVo.setStoreName(storeMapper.selectByPrimaryKey(goods.getStoreid()).getStorename());
        double highPraiseRate= commentMapper.findFiveStar(goodsId)/ commentMapper.findTotal(goodsId).doubleValue()*100;
        DecimalFormat df = new DecimalFormat("#.00");
        String s = df.format(highPraiseRate);
        if (s.length() >= 2){
            s = s.substring(0, 2);
        }else {
            s = "100";
        }
        goodsVo.setHighPraiseRate(s+"%");
        return goodsVo;
    }

    //热卖商品
    @Override
    public List<Goods> findSellMore() {
        return goodsMapper.findSellMore();
    }

    //好评（推荐）商品
    @Override
    public List<Goods> findRecommendGoods() {
        List<Long> goodsIds = commentMapper.findRecommendId();
        Long[] goodsIdsArr = new Long[goodsIds.size()];
        for (int i = 0; i < goodsIds.size(); i++) {
            goodsIdsArr[i] = goodsIds.get(i);
        }
        return goodsMapper.findRecommend(goodsIdsArr);
    }

    //用户按各种条件查询
    @Override
    public PageInfo<GoodsVo> findGoods(Long firstTypeId,
                                           Long secondTypeId,
                                           String goodsName,
                                           Double minPrice,
                                           Double maxPrice,
                                           int currentPage,
                                           int pageSize,
                                           Long storeid) {
        //设置分页信息
        PageHelper.startPage(currentPage, pageSize);

        List<Goods> goodsList = goodsMapper.findGoods(firstTypeId,secondTypeId,goodsName,minPrice,maxPrice,storeid);

        //创建分页VO对象
        PageInfo goodsPageInfo = new PageInfo<>(goodsList);

        List<GoodsVo> goodsVoList = new ArrayList<>();

        for (Goods goods : goodsList) {
            GoodsVo goodsVo = new GoodsVo();
            BeanUtils.copyProperties(goods, goodsVo);

            goodsVo.setFirsttypeName(firstTypeMapper.selectByPrimaryKey(goods.getFirsttypeid()).getFirsttypename());
            goodsVo.setSecondtypeName(secondTypeMapper.selectByPrimaryKey(goodsVo.getSecondtypeid()).getSecondtypename());
            goodsVo.setStoreName(storeMapper.selectByPrimaryKey(goodsVo.getStoreid()).getStorename());

            if (goodsVo.getStatus() == 1){
                goodsVo.setStatusName("已上架");
            }else {
                goodsVo.setStatusName("已下架");
            }

            goodsVoList.add(goodsVo);
        }

        goodsPageInfo.setList(goodsVoList);

        return goodsPageInfo;
    }

    //管理员按各种条件查询
    @Override
    public PageInfo<GoodsVo> findGoodsBack(Long firstTypeId,
                                     Long secondTypeId,
                                     String goodsName,
                                     Double minPrice,
                                     Double maxPrice,
                                     int currentPage,
                                     int pageSize,
                                           String store) {

        Long storeid;
        if (store.equals("XX")){
            storeid = 0L;
        }else {
            storeid = storeMapper.selectByName(store);
        }

        //设置分页信息
        PageHelper.startPage(currentPage, pageSize);

        List<Goods> goodsList = goodsMapper.findGoodsBack(firstTypeId,secondTypeId,goodsName,minPrice,maxPrice,storeid);

        //创建分页VO对象
        PageInfo goodsPageInfo = new PageInfo<>(goodsList);

        List<GoodsVo> goodsVoList = new ArrayList<>();

        for (Goods goods : goodsList) {
            GoodsVo goodsVo = new GoodsVo();
            BeanUtils.copyProperties(goods, goodsVo);
            System.out.println("------------------>"+goods);
            goodsVo.setFirsttypeName(firstTypeMapper.selectByPrimaryKey(goods.getFirsttypeid()).getFirsttypename());
            goodsVo.setSecondtypeName(secondTypeMapper.selectByPrimaryKey(goods.getSecondtypeid()).getSecondtypename());
            goodsVo.setStoreName(storeMapper.selectByPrimaryKey(goodsVo.getStoreid()).getStorename());

            if (goods.getStatus() == 1){
                goodsVo.setStatusName("已上架");
            }else {
                goodsVo.setStatusName("已下架");
            }

            goodsVoList.add(goodsVo);
        }

        goodsPageInfo.setList(goodsVoList);

        return goodsPageInfo;
    }

    //通过店铺id查询
    @Override
    public List<Goods> findByStoreId(Long storeId) {
        return goodsMapper.findByStoreId(storeId);
    }

    //查找全部一级分类
    @Override
    public List<FirstType> findAllFirstType() {
        return firstTypeMapper.findAll();
    }

    //查找全部二级分类
    @Override
    public PageInfo<SecondTypeVo> findAllSecondType(int currentPage, int pageSize) {

        //设置分页信息
        PageHelper.startPage(currentPage, pageSize);

        List<SecondType> list = secondTypeMapper.findAll();

        //创建分页VO对象
        PageInfo secondTypePageInfo = new PageInfo<>(list);

        List<SecondTypeVo> secondTypeVoList = new ArrayList<>();
        for (SecondType secondType : list) {

            SecondTypeVo secondTypeVo = new SecondTypeVo();
            BeanUtils.copyProperties(secondType, secondTypeVo);
            secondTypeVo.setFirsttypeName(firstTypeMapper.selectByPrimaryKey(secondType.getFirsttypeid()).getFirsttypename());

            if (secondType.getStatus() == 1){
                secondTypeVo.setStatusName("存在类别");
            }else {
                secondTypeVo.setStatusName("已不存在类别");
            }

            secondTypeVoList.add(secondTypeVo);

        }

        secondTypePageInfo.setList(secondTypeVoList);

        return secondTypePageInfo;
    }

    //通过一级分类id查找二级分类
    @Override
    public List<SecondType> findAllSecondTypeByFirstId(Long firstTypeId) {
        return secondTypeMapper.findByFirstId(firstTypeId);
    }

    //添加第二类别
    @Override
    public boolean addSecondType(String secondtypeName,Long firsttypeid) {
        SecondType secondType = new SecondType();
        secondType.setFirsttypeid(firsttypeid);
        secondType.setCreatetime(new Date());
        secondType.setSecondtypename(secondtypeName);
        return secondTypeMapper.insertSelective(secondType) > 0;
    }

    //添加一级类别
    @Override
    public boolean addFirstType(String firsttypeName) {
        FirstType firstType = new FirstType();
        firstType.setCreatetime(new Date());
        firstType.setFirsttypename(firsttypeName);
        return firstTypeMapper.insertSelective(firstType) > 0;
    }

    //删除二级类别
    @Override
    public boolean deleteSecondType(Long secondtypeid) {

        SecondType secondType = secondTypeMapper.selectByPrimaryKey(secondtypeid);
        secondType.setStatus(0);

        return secondTypeMapper.updateByPrimaryKeySelective(secondType) > 0;
    }

    //更新二级类别
    @Override
    public boolean upadateSecondType(Long secondtypeid, String secondtypeName) {
        SecondType secondType = secondTypeMapper.selectByPrimaryKey(secondtypeid);
        secondType.setSecondtypename(secondtypeName);
        return secondTypeMapper.updateByPrimaryKeySelective(secondType) > 0;
    }
}
