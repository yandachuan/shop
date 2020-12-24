package com.neuedu.vo;

import lombok.Data;


@Data
public class OrderGoodsVO {
    private Long id;

    private Long orderid;

    //private Long goodsid;
    private String goodsname;
    private Double price;
    private String imag0;

    private Integer num;



}
