package com.cq.gmall.seckill.designmode.adapter;

/**
 * @author 彭国仁
 * @data 2019/12/21 22:41
 */
public class PhoneCharging {
    public void Charging(IVoltage5V iVoltage5V) {
        int i = iVoltage5V.ouput5V();
        if (i == 5) {
            System.out.println("开始充电");
        }else {
            System.out.println("电压过高");
        }
    }
}
