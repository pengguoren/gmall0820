package com.cq.gmall.service;


import com.cq.gmall.bean.UmsMember;
import com.cq.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/21 18:11
 */
public interface UserService {
    List<UmsMember> getAllUser();

    UmsMember login(UmsMember umsMember);

    /**
     * 将token存入redis一份
     * @param token
     * @param memberId
     */
    void addUserToken(String token, String memberId);

    /**
     * 社交登录用户信息保存
     * @param umsMember
     * @return
     */
    UmsMember addOauthUser(UmsMember umsMember);

    /**
     * 检查社交用户是否登陆过
     * @param umsCheck
     * @return
     */
    UmsMember checkOauthUser(UmsMember umsCheck);

    /**
     * 根据收货地址id，查询收货地址详细信息
     * @param receiveAddressId
     * @return
     */
    UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId);
}
