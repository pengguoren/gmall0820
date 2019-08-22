package com.cq.gmall.user.service.impl;

import com.cq.gmall.user.bean.UmsMember;
import com.cq.gmall.user.mapper.UserMapper;
import com.cq.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/21 18:12
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<UmsMember> getAllUser() {
        return userMapper.selectAllUser();
    }
}
