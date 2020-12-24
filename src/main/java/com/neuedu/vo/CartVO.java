package com.neuedu.vo;

import lombok.Data;



@Data
public class CartVO {
    private Long cartid;

    private Long userid;

    //private Long goodsid;  //支付时需要
    private Double price;
    private String goodsname;
    private String imag0;


    private Integer quantity;
    private Integer isChecked;

}
