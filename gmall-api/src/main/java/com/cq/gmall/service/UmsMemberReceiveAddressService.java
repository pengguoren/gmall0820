package com.cq.gmall.service;

import com.cq.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/22 17:08
 */
public interface UmsMemberReceiveAddressService {
    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
