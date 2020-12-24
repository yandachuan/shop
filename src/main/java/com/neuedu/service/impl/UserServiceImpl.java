package com.neuedu.service.impl;

import com.neuedu.entity.User;
import com.neuedu.mapper.UserMapper;
import com.neuedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUserEmail(String email, String password) {
        return userMapper.findUserByEmail(email, password);
    }

    @Override
    public User findUserByUserId(Long userid, String password) {
        return userMapper.findUserByUserId(userid, password);
    }

    @Override
    public User findUserByLoginphone(String loginphone, String password) {
        return userMapper.findUserByLoginphone(loginphone, password);
    }

    @Override
    public boolean findUserByphone(String loginphone) {
        return userMapper.findUserByphone(loginphone)>0;
    }

    @Override
    public boolean reg(User user) {
        return userMapper.insertSelective(user)>0;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user)>0;
    }
}
