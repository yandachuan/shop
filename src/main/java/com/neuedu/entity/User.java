package com.neuedu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long userid;

    private String loginphone;

    private String email;

    private String password;

    private String nickname;

    private String gender;

    private Integer status;     //状态

    private String userheadportrait;    //用户头像

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy年MM月dd日")
    private Date createtime;    //创建时间


}