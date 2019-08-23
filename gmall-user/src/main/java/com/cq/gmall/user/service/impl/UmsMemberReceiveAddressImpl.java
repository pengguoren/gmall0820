package com.cq.gmall.user.service.impl;

import com.cq.gmall.bean.UmsMemberReceiveAddress;
import com.cq.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.cq.gmall.service.UmsMemberReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/22 17:08
 */
@Service
public class UmsMemberReceiveAddressImpl implements UmsMemberReceiveAddressService {
    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberid) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberid);
        return umsMemberReceiveAddressMapper.select(new UmsMemberReceiveAddress());
    }
}
