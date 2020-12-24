package com.neuedu.vo;

import lombok.Data;

import java.util.Date;
@Data
public class CommentVO {
    private Long commentId;
    private Long goodsId;

    //private Long userId;
    private String nickname;
    private String userheadportrait;

    private String content;
    private Integer star;
    private Long lastid;
    private String imag0;
    private String imag1;
    private String imag2;
    //private Date createTime;
    private String createtime;
}
