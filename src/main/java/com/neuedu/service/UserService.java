package com.neuedu.service;

import com.neuedu.entity.User;

public interface UserService {

    //根据邮箱和密码查找用户
    User findUserByUserEmail(String email, String password);

    //根据用户ID和密码查找用户
    User findUserByUserId(Long userid, String password);

    //根据手机号和密码查找用户
    User findUserByLoginphone(String loginphone, String password);

    //注册时根据手机号查询用户是否存在
    boolean findUserByphone(String loginphone);

    //注册用户
    boolean reg(User user);

    //更新用户信息
    boolean updateUser(User user);
}
