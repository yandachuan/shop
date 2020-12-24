package com.neuedu.entity;

import lombok.Data;

@Data
public class OrderGoods {
    private Long id;

    private Long orderid;

    private Long goodsid;

    private Integer num;


}