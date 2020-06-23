package com.cq.gmall.seckill.datastruct;

/**
 * @author 彭国仁
 * @data 2019/12/7 12:53
 */
/*
八皇后问题算法思路分析

        第一个皇后先放第一行第一列
        第二个皇后放在第二行第一列、然后判断是否OK， 如果不OK，继续放在第二列、第三列、依次把所有列都放完，找到一个合适
        继续第三个皇后，还是第一列、第二列……直到第8个皇后也能放在一个不冲突的位置，算是找到了一个正确解
        当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解，全部得到.
        然后回头继续第一个皇后放第二列，后面继续循环执行 1,2,3,4的步骤 【示意图】

        说明：理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，用一个一维数组即可解决问题. arr[8] = {0 , 4, 7, 5, 2, 6, 1, 3} //对应arr 下标 表示第几行，即第几个皇后，arr[i] = val , val 表示第i+1个皇后，放在第i+1行的第val+1列
*/

public class EightQueen {
    private  int max = 8;
    private  int queen [] = new int[8];
    private  int count;
    public static void main(String[] args) {
        EightQueen eightQueen = new EightQueen();
        eightQueen.check(0);
        System.out.println("总共有多少种解法："+eightQueen.count);
    }

    //检查第n+1个皇后
    public  void check(int n) {
        if (n == 8) {
            count++;
            for (int i = 0; i <max ; i++) {
                System.out.print(queen[i]+" ");
            }
            System.out.println();
            return;
        }
        for (int i = 0; i < max; i++) {
            queen[n] = i;
            if (judge(n)) {
                check(n+1);
            }
        }

    }
    //判断皇后的位置是否合法
    public  boolean judge(int n) {
        for (int i = 0; i <n ; i++) {
            if (queen[i] == queen[n]||Math.abs(n-i)==Math.abs(queen[i]-queen[n])) {
                return false;
            }
        }
        return true;
    }
}
