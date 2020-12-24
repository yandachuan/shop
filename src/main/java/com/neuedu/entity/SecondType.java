package com.neuedu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SecondType {
    private Long secondtypeid;

    private String secondtypename;

    private Integer status;

    private Long firsttypeid;

    private Date createtime;


}