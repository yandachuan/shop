package com.neuedu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SecondTypeVo {

    private Long secondtypeid;

    private String secondtypename;

    private Integer status;

    private Long firsttypeid;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String statusName;

    private String firsttypeName;

}
