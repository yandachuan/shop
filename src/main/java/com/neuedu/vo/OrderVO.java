package com.neuedu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderVO {
    private Long orderid;

    private Long userid;

    private Long addressid;

    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private Date createtime;

    private Double totalPrice;    //总价格

    private List<OrderGoodsVO> OrderGoodsVOList;   //一对多关联

}
