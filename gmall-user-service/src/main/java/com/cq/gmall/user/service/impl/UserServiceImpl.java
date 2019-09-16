package com.cq.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cq.gmall.bean.UmsMember;
import com.cq.gmall.service.UserService;
import com.cq.gmall.user.mapper.UserMapper;
import com.cq.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/21 18:12
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<UmsMember> getAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public UmsMember login(UmsMember umsMember) {
        Jedis jedis = redisUtil.getJedis();
        try {
            if (jedis != null) {
                String umsMemberStr = jedis.get("user:" + umsMember.getUsername() + umsMember.getPassword() + ":info");
                if (StringUtils.isNotBlank(umsMemberStr)) {
                    UmsMember UmsMemberFromCache = JSON.parseObject(umsMemberStr, UmsMember.class);
                    return UmsMemberFromCache;
                }
            }
            //链接redis查询失败，开启数据库
            //需要加分布式锁，这里没加
            UmsMember umsMemberFromDb = loginFromDb(umsMember);
            if (umsMemberFromDb != null) {
                jedis.setex("user:" + umsMember.getUsername() + umsMember.getPassword() + ":info", 60 * 60 * 24, JSON.toJSONString(umsMemberFromDb));
            }
            return umsMemberFromDb;
        } finally {
            jedis.close();
        }
    }

    /**
     * 将token存入redis一份
     *
     * @param token
     * @param memberId
     */
    @Override
    public void addUserToken(String token, String memberId) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            jedis.setex("user:" + memberId + ":token", 60 * 60 * 24, token);
        } finally {
            jedis.close();
        }
    }

    private UmsMember loginFromDb(UmsMember umsMember) {
        UmsMember umsMemberFromDb = userMapper.selectOne(umsMember);
        return umsMemberFromDb;
    }
}
