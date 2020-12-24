package com.neuedu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserStore {
    private Long id;

    private Long userid;

    private Long storeid;

    private String headportrait;

    private String storename;

    private Date createtime;


}