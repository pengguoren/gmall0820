package com.cq.gmall.service;


import com.cq.gmall.bean.UmsMember;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/21 18:11
 */
public interface UserService {
    List<UmsMember> getAllUser();

    UmsMember login(UmsMember umsMember);

    void addUserToken(String token, String memberId);
}
