package com.cq.gmall.seckill.datastruct;

import java.util.Random;

/**
 * @author 彭国仁
 * @data 2019/12/6 22:46
 */
public class Ball {
    public static void main(String[] args) {

        for (int i = 0; i <100 ; i++) {
            int num = new Random().nextInt(4);
            System.out.println(num);
        }

    }
}
//    baskets will be between 1 and 5, inclusive.
//        篮子数量在1到5之间
//        -
//        capacity will be between 1 and 20, inclusive.
//        容量在1到20之间
//        -
//        balls will be between 1 and 100, inclusive.
//        球在1到100之间
class BallBasket{

    public int countWays(int baskets, int capacity, int balls){
        return 0;
    }
}