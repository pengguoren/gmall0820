package com.cq.gmall.order.mq;

import com.cq.gmall.bean.OmsOrder;
import com.cq.gmall.mq.ActiveMQUtil;
import com.cq.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @author 彭国仁
 * @data 2019/11/3 16:42
 */
@Component
public class OrderServiceMqListener {
    @Autowired
    OrderService orderService;

    @Autowired
    ActiveMQUtil activeMQUtil;

    @JmsListener(destination = "PAYHMENT_SUCCESS_QUEUE",containerFactory = "jmsQueueListener")
    public void consumePaymentResult(MapMessage mapMessage) throws JMSException {
        String out_trade_no = mapMessage.getString("out_trade_no");
        //更新订单业务状态
        System.out.println(out_trade_no);
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderSn(out_trade_no);

        orderService.updateOrder(omsOrder);

        System.out.println("11111111111111");
    }
}
