package com.cq.gmall.user.mapper;

import com.cq.gmall.user.bean.UmsMember;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/21 18:26
 */
public interface UserMapper {
    List<UmsMember> selectAllUser();
}
