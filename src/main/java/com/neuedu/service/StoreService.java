package com.neuedu.service;

import com.neuedu.entity.Store;

public interface StoreService {
    Store findStore (Long storeid);

    boolean addStore(Store store);

    boolean updateStore(Store store);

    boolean existStore(String storename);
}
