package com.neuedu.entity;

import lombok.Data;

@Data
public class Address {
    private Long addressid;

    private Long userid;

    private String receiver;

    private String tel;

    private String address;

    private Integer isDefault;


}