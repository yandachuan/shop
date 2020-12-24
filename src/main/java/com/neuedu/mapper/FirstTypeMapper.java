package com.neuedu.mapper;

import com.neuedu.entity.FirstType;

import java.util.List;

public interface FirstTypeMapper {
    int deleteByPrimaryKey(Long firsttypeid);

    int insert(FirstType record);

    int insertSelective(FirstType record);

    FirstType selectByPrimaryKey(Long firsttypeid);

    int updateByPrimaryKeySelective(FirstType record);

    int updateByPrimaryKey(FirstType record);

    List<FirstType> findAll();//查找全部一级分类
}