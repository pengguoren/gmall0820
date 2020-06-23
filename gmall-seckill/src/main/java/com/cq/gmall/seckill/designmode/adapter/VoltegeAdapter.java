package com.cq.gmall.seckill.designmode.adapter;

/**
 * @author 彭国仁
 * @data 2019/12/21 22:40
 */
public class VoltegeAdapter extends Voltage220V implements IVoltage5V {

    @Override
    public int ouput5V() {
        int src = output();

        return src / 44;
    }
}
