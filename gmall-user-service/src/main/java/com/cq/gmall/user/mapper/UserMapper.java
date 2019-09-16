package com.cq.gmall.user.mapper;

import com.cq.gmall.bean.UmsMember;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/21 18:26
 */
public interface UserMapper extends Mapper<UmsMember> {
    List<UmsMember> selectAllUser();
}
