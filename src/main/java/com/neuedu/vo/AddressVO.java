package com.neuedu.vo;

import lombok.Data;


@Data
public class AddressVO {


    private Long id;

    private String name;

    private String tel;

    private String address;

    private Integer isDefault;
}
