package com.neuedu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private Long orderid;

    private Long userid;

    private Long addressid;

    private Date createtime;


}