package com.cq.gmall.service;

import com.cq.gmall.bean.OmsOrder;
import org.springframework.stereotype.Service;

/**
 * @author 彭国仁
 * @data 2019/9/18 16:18
 */
public interface OrderService {

    String getTradeCode(String memberId);

    String checkTradeCode(String memberId, String tradeCode);

    /**
     * 将订单和订单详情写入数据库
     * @param omsOrder
     */
    void saveOrder(OmsOrder omsOrder);

    OmsOrder getOrderByOutTradeNo(String outTradeNo);
}
