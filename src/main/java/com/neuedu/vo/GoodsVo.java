package com.neuedu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GoodsVo {
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String highPraiseRate;//好评率

    private String statusName;
    private String firsttypeName;
    private String secondtypeName;
    private String storeName;

}
