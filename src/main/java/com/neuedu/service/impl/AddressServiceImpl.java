package com.neuedu.service.impl;


import com.neuedu.entity.Address;
import com.neuedu.entity.User;
import com.neuedu.mapper.AddressMapper;
import com.neuedu.mapper.UserMapper;
import com.neuedu.service.AddressService;
import com.neuedu.vo.AddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public AddressVO getAddress(Long addressid) {

        Address address = addressMapper.selectByPrimaryKey(addressid);

        AddressVO addressVO = new AddressVO();

        BeanUtils.copyProperties(address, addressVO);

        addressVO.setId(addressid);
        addressVO.setAddress(address.getReceiver()+address.getAddress());

        User user = userMapper.selectByPrimaryKey(address.getUserid());
        addressVO.setName(user.getNickname());


        return addressVO;
    }

    @Override
    public List<AddressVO> getAddressList(Long userId) {

        List<Address> addressList = addressMapper.findAddressList(userId);
        List<AddressVO> addressVOList = new ArrayList<>();
        for (Address address : addressList) {
            AddressVO addressVO = new AddressVO();

            BeanUtils.copyProperties(address, addressVO);

            addressVO.setId(address.getAddressid());
            addressVO.setAddress(address.getReceiver()+address.getAddress());

            User user = userMapper.selectByPrimaryKey(address.getUserid());
            addressVO.setName(user.getNickname());
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Override
    public Boolean addAddress(Address address) {
        return addressMapper.insert(address)>0;
    }

    @Override
    public Boolean delAddress(Long addressid) {
        return addressMapper.deleteByPrimaryKey(addressid)>0;
    }

    @Override
    public Boolean updateAddress(Address address) {
        return addressMapper.updateByPrimaryKeySelective(address)>0;

    }
}
