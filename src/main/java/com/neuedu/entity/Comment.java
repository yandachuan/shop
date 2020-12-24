package com.neuedu.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Comment {
    private Long commentid;

    private Long goodsid;

    private Long userid;

    private String content;

    private Integer star;

    private Long lastid;

    private String imag0;

    private String imag1;

    private String imag2;

    private Date createtime;


}