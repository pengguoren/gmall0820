package com.cq.gmall.service;

import com.cq.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/22 17:08
 */
public interface UmsMemberReceiveAddressService {
    /**
     * 根据用户id查询收货地址列表
     * @param memberId
     * @return
     */
    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
