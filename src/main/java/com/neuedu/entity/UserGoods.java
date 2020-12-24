package com.neuedu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserGoods {
    private Long id;

    private Long userid;

    private Long goodsid;

    private String imag0;

    private String goodsname;

    private Double price;

    private Integer collectionnum;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy年MM月dd日")
    private Date createtime;


}