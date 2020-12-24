package com.neuedu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Goods {
    private Long goodsid;

    private Double price;

    private String goodsname;

    private Integer monthlysales;

    private Integer inventory;

    private String imag0;

    private String imag1;

    private String imag2;

    private String details;

    private Long firsttypeid;

    private Long secondtypeid;

    private Long storeid;

    private Integer discount;

    private String originaddress;

    private Integer freight;

    private Integer collectionnum;

    private Integer status;

    private Date createtime;

}