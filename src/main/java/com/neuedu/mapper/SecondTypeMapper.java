package com.neuedu.mapper;

import com.neuedu.entity.SecondType;

import java.util.List;

public interface SecondTypeMapper {
    int deleteByPrimaryKey(Long secondtypeid);

    int insert(SecondType record);

    int insertSelective(SecondType record);

    SecondType selectByPrimaryKey(Long secondtypeid);

    int updateByPrimaryKeySelective(SecondType record);

    int updateByPrimaryKey(SecondType record);

    List<SecondType> findAll();//查找全部二级分类

    List<SecondType> findByFirstId(Long firstTypeId);//通过一级分类的id查找二级分类
}