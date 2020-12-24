package com.neuedu.service.impl;

import com.neuedu.entity.Store;
import com.neuedu.mapper.SecondTypeMapper;
import com.neuedu.mapper.StoreMapper;
import com.neuedu.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Store findStore(Long storeid) {

        return storeMapper.selectByPrimaryKey(storeid);
    }

    @Override
    public boolean addStore(Store store) {


        return storeMapper.insertSelective(store)>0;
    }

    @Override
    public boolean updateStore(Store store) {
        return storeMapper.updateByPrimaryKeySelective(store)>0;
    }

    @Override
    public boolean existStore(String storename) {
        return storeMapper.findStoreCount(storename)>0;
    }
}
