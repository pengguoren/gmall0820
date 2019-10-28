package com.cq.gmall.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cq.gmall.bean.PaymentInfo;
import com.cq.gmall.payment.mapper.PaymentMapper;
import com.cq.gmall.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

/**
 * @author 彭国仁
 * @data 2019/10/28 9:57
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentMapper paymentMapper;

    /**
     *  生成并保存用户的支付信息
     * @param paymentInfo
     */
    @Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
        paymentMapper.insertSelective(paymentInfo);

    }
    /**
     *   验签成功
     *   更新用户的支付状态
     * @param paymentInfo
     */
    @Override
    public void updatePayment(PaymentInfo paymentInfo) {
        String orderSn = paymentInfo.getOrderSn();
        Example e = new Example(PaymentInfo.class);
        e.createCriteria().andEqualTo("orderSn", orderSn);
        paymentMapper.updateByExample(paymentInfo, e);
    }
}
