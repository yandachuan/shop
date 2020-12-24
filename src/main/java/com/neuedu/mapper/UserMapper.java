package com.neuedu.mapper;

import com.neuedu.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Long userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //根据用户ID和密码查找用户
    User findUserByUserId(@Param("userid") Long userid,@Param("password") String password);

    //根据邮箱和密码查找用户
    User findUserByEmail(@Param("email") String email,@Param("password") String password);

    //根据手机号和密码查找用户
    User findUserByLoginphone(@Param("loginphone") String loginphone,@Param("password") String password);

    //注册时根据手机号查询用户是否存在
    int findUserByphone(String loginphone);
}