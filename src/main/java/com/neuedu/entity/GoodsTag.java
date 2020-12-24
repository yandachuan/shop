package com.neuedu.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class GoodsTag {
    private Long id;

    private Long goodsid;

    private String tag0;

    private String tag1;

    private BigDecimal price;

    private String inventory;

    private Date createtime;


}