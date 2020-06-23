package com.cq.gmall.seckill.designmode.chain_responsibility;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 彭国仁
 * @data 2020/2/25 9:47
 */
public class Responsibility {
    public static void main(String[] args) {
        Buyer buyer = new Buyer(1,"小李");
        approve(buyer,20000);
    }


    public static void approve(Buyer buyer, int sum) {
        System.out.println("采购员："+buyer+"采购了一批价值"+sum+"的器材等待审批");
        if (sum>=0&&sum<=5000) {
            System.out.println("教学主任审批");
        } else if (sum>5000&&sum<=10000) {
            System.out.println("院长审批");
        } else if (sum>10000&&sum<=30000) {
            System.out.println("副校长审批");
        } else if(sum>30000) {
            System.out.println("校长审批");
        }
    }
}
//OA系统采购审批需求
//        学校OA系统的采购审批项目:需求是
//        1)采购员采购教学器材
//        2)如果金额小于等于5000由教学主任审批(0<=x<=5000
//        3)如果金额小于等于10000由院长审批(500X<=-10000
//        4)如果金额小于等于30000d由副校长审批(10000=30000
//        5)如果金额超过30000以上,有校长审批(30000×
//        请设计程序完成采购审批项目
