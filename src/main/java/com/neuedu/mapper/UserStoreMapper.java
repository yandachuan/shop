package com.neuedu.mapper;

import com.neuedu.entity.UserStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserStoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserStore record);

    int insertSelective(UserStore record);

    UserStore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserStore record);

    int updateByPrimaryKey(UserStore record);

    boolean deleteRecord(@Param("storeid") Long storeid, @Param("userid") Long userid);

    int selectBySidAndUid(@Param("storeid") Long storeid, @Param("userid") Long userid);

    List<UserStore> selectStoreList(Long userid);
}