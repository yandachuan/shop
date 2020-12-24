package com.neuedu.entity;

import lombok.Data;

@Data
public class Cart {
    private Long cartid;

    private Long userid;

    private Long goodsid;

    private Integer quantity;

    private Integer ischecked;

}