package com.cq.gmall.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cq.gmall.bean.OmsOrder;
import com.cq.gmall.bean.OmsOrderItem;
import com.cq.gmall.order.mapper.OmsOrderItemMapper;
import com.cq.gmall.order.mapper.OmsOrderMapper;
import com.cq.gmall.service.CartService;
import com.cq.gmall.service.OrderService;
import com.cq.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author 彭国仁
 * @data 2019/9/18 16:25
 */
@Service
public class orderServiceImpl implements OrderService {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    OmsOrderMapper omsOrderMapper;

    @Autowired
    OmsOrderItemMapper omsOrderItemMapper;

    @Reference
    CartService cartService;

    @Override
    public String checkTradeCode(String memberId, String tradeCode) {
        String flag = null;
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            flag = "fail";
            String tradeKey = "user:" + memberId + ":tradeCode";
            //使用lua脚本在发现key的同时将key删除，防止并发订单攻击。
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Long eval = (Long) jedis.eval(script, Collections.singletonList(tradeKey),
                    Collections.singletonList(tradeCode));
            if (eval != null && eval != 0) {
                //jedis.del(tradeKey);
                flag = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return flag;
    }

    @Override
    public void saveOrder(OmsOrder omsOrder) {
        //保存订单表
        omsOrderMapper.insertSelective(omsOrder);
        //保存订单详情表
        List<OmsOrderItem> omsOrderItems = omsOrder.getOmsOrderItems();
        for (OmsOrderItem omsOrderItem : omsOrderItems) {
            omsOrderItem.setOrderId(omsOrder.getId());
            omsOrderItemMapper.insertSelective(omsOrderItem);
            //删除购物车数据
            cartService.removeCartData(omsOrderItem.getProductSkuId());
        }
        //刷新缓存
        cartService.flushCartCache(omsOrder.getMemberId());
    }

    /**
     * 根据外部订单号查询订单信息
     * @param outTradeNo
     * @return
     */
    @Override
    public OmsOrder getOrderByOutTradeNo(String outTradeNo) {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderSn(outTradeNo);
        omsOrderMapper.select(omsOrder);
        return omsOrder;
    }

    @Override
    public String getTradeCode(String memberId) {
        String tradeCode = null;
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            String tradeKey = "user:" + memberId + ":tradeCode";
            tradeCode = UUID.randomUUID().toString();
            jedis.setex(tradeKey, 60 * 15, tradeCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return tradeCode;
    }
}
