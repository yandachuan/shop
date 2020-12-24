package com.neuedu.service;

import com.neuedu.entity.UserStore;

import java.util.List;

public interface UserStoreService {

    //收藏店铺
    boolean collectStore(Long storeid, Long userid);

    //取消收藏店铺
    boolean cancleStore(Long storeid, Long userid);

    //当前用户是否关注店铺
    boolean isFollow(Long storeid, Long userid);

    List<UserStore> selectStoreList(Long userid);
}
