package com.cq.gmall.service;

import com.cq.gmall.bean.PaymentInfo;

/**
 * @author 彭国仁
 * @data 2019/10/28 9:55
 */
public interface PaymentService {
    /**
     *  生成并保存用户的支付信息
     * @param paymentInfo
     */
    void savePaymentInfo(PaymentInfo paymentInfo);

    /**
     *   验签成功
     *   更新用户的支付状态
     * @param paymentInfo
     */
    void updatePayment(PaymentInfo paymentInfo);
}
