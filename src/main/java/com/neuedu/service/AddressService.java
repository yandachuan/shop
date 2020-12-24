package com.neuedu.service;

import com.neuedu.entity.Address;
import com.neuedu.vo.AddressVO;

import java.util.List;

public interface AddressService {

    AddressVO getAddress(Long addressid);

    List<AddressVO> getAddressList(Long userId);

    // 添加地址业务
    Boolean addAddress(Address address);

    // 删除地址
    Boolean delAddress(Long addressid);

    // 修改地址
    Boolean updateAddress(Address address);

}
