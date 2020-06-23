package com.cq.gmall.seckill.datastruct.algorithm;

/**
 * @author 彭国仁
 * @data 2019/12/16 7:09
 */
public class HanNuoTa {


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(iteration(64));
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }


    /**
     * 递归
     * @param n
     * @return
     */
    public static long recursion(long n) {
        if (n == 1) {
            return 1;
        }
        return 2 * recursion(n - 1) + 1;
    }

    public static long iteration(long n) {
        if (n == 1) {
            return 1;
        }
        long one = 1;
        long sum = 0;
        for (int i = 2; i <n ; i++) {
            sum = 2 * one + 1;
            one = sum;
        }
        return sum;
    }
}
