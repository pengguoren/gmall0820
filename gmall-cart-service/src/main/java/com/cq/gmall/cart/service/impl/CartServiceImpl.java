package com.cq.gmall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cq.gmall.bean.OmsCartItem;
import com.cq.gmall.cart.mapper.OmsCartItemMapper;
import com.cq.gmall.service.CartService;
import com.cq.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 彭国仁
 * @data 2019/9/13 15:46
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    OmsCartItemMapper omsCartItemMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public OmsCartItem getCartItemBySkuId(String skuId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setProductSkuId(skuId);
        return omsCartItemMapper.selectOne(omsCartItem);
    }

    @Override
    public void addCart(OmsCartItem omsCartItem) {
        if (StringUtils.isNotBlank(omsCartItem.getMemberId())) {
            omsCartItemMapper.insertSelective(omsCartItem);
        }
    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {
        Example e = new Example(OmsCartItem.class);
        e.createCriteria().andEqualTo("id", omsCartItemFromDb.getId());
        omsCartItemMapper.updateByExampleSelective(omsCartItemFromDb, e);
    }


    @Override
    public void flushCartCache(String memberId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        List<OmsCartItem> omsCartItems = omsCartItemMapper.select(omsCartItem);
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            //删除缓存
            jedis.del(memberId);
            Map<String, String> map = new HashMap<>();
            for (OmsCartItem cartItem : omsCartItems) {
                map.put(cartItem.getProductSkuId(), JSON.toJSONString(cartItem));
            }

            jedis.hmset(memberId,map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    @Override
    public List<OmsCartItem> getCartList(String memberId) {
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        Jedis jedis = redisUtil.getJedis();

        try {
            List<String> hvals = jedis.hvals(memberId);
            for (String hval : hvals) {
                OmsCartItem omsCartItem = JSON.parseObject(hval, OmsCartItem.class);
                omsCartItems.add(omsCartItem);
            }

            //缓存失效时查询数据库
            if (omsCartItems==null) {
                OmsCartItem omsCartItem = new OmsCartItem();
                omsCartItem.setMemberId(memberId);
                omsCartItems = omsCartItemMapper.select(omsCartItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return omsCartItems;
    }

    @Override
    public void checkCart(OmsCartItem omsCartItem) {
        Example e = new Example(OmsCartItem.class);
        e.createCriteria().andEqualTo("productSkuId", omsCartItem.getProductSkuId());
        omsCartItemMapper.updateByExampleSelective(omsCartItem,e);
        //同步缓存
       flushCartCache(omsCartItem.getMemberId());
    }

    @Override
    public void removeCartData(String productSkuId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setProductSkuId(productSkuId);
        omsCartItemMapper.delete(omsCartItem);
        //刷新缓存
    }


}
