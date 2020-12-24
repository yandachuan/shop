package com.neuedu.service.impl;

import com.neuedu.entity.Store;
import com.neuedu.entity.UserStore;
import com.neuedu.mapper.StoreMapper;
import com.neuedu.mapper.UserStoreMapper;
import com.neuedu.service.UserStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserStoreServiceImpl implements UserStoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private UserStoreMapper userStoreMapper;

    @Override
    public boolean collectStore(Long storeid, Long userid) {

        System.out.println("collectStore");

        UserStore userStore = new UserStore();
        Store store = storeMapper.selectByPrimaryKey(storeid);

        userStore.setUserid(userid);
        userStore.setStoreid(storeid);
        userStore.setHeadportrait(store.getStoreheadportrait());
        userStore.setStorename(store.getStorename());
        userStore.setCreatetime(new Date());

        //修改店铺粉丝数
        store.setFansnum(store.getFansnum()+1);
        storeMapper.updateByPrimaryKey(store);

        return userStoreMapper.insertSelective(userStore)>0;
    }

    @Override
    public boolean cancleStore(Long storeid, Long userid) {
        System.out.println("cancleStore");
        Store store = storeMapper.selectByPrimaryKey(storeid);
        //修改店铺粉丝数
        store.setFansnum(store.getFansnum()+1);
        storeMapper.updateByPrimaryKey(store);

        return userStoreMapper.deleteRecord(storeid,userid);
    }

    @Override
    public boolean isFollow(Long storeid, Long userid) {
        return userStoreMapper.selectBySidAndUid(storeid, userid)>0;
    }

    @Override
    public List<UserStore> selectStoreList(Long userid) {
        return userStoreMapper.selectStoreList(userid);
    }
}
