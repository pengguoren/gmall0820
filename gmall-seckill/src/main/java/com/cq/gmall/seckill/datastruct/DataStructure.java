package com.cq.gmall.seckill.datastruct;


/**
 * @author 彭国仁
 * @data 2019/12/3 8:37
 */
public class DataStructure {

    /* public static void main(String[] args) {
       //五子棋存盘和续上盘
        //0表示没子，1表示黑子，2表示篮子
        int chess[][] = new int[11][11];
        chess[2][3] = 1;
        chess[3][4] = 2;
        chess[3][5] = 2;
        for (int[] row : chess) {
            for (int data : row) {
                System.out.print("\t"+data);
            }
            System.out.println();
        }
        //将二维数组转化为稀疏数组
        int sum = 0;
        for (int i = 0; i <11 ; i++) {
            for (int j = 0; j <11; j++) {
                if (chess[i][j]!=0) {
                    sum += 1;
                }
            }
        }
        int sparArr[][] = new int[sum + 1][3];
        sparArr[0][0] = 11;
        sparArr[0][1] = 11;
        sparArr[0][2] = sum;
        int count = 0;
        for (int i = 0; i <11 ; i++) {
            for (int j = 0; j <11; j++) {
                if (chess[i][j]!=0) {
                    count++;
                    sparArr[count][0] = i;
                    sparArr[count][1] = j;
                    sparArr[count][2] = chess[i][j];
                }
            }
        }
        System.out.println("----------------");

        for (int[] row : sparArr) {
            for (int data : row) {
                System.out.print("\t"+data);
            }
            System.out.println();
        }

        //稀疏数组转二维数组
        int newChess[][] = new int[sparArr[0][0]][sparArr[0][1]];
        for (int i = 0; i <sparArr.length ; i++) {
            if (i > 0) {
                newChess[sparArr[i][0]][sparArr[i][1]] = sparArr[i][2];
            }
        }
        System.out.println("打印续上盘的二维数组-----------------------------");
        for (int[] row : newChess) {
            for (int data : row) {
                System.out.print("\t"+data);
            }
            System.out.println();
        }
    }*/

    private static  int num = 0;
    private static ThreadLocal<Integer> numLocal = ThreadLocal.withInitial(()->2);
    public static void main(String[] args) {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                int x = numLocal.get().intValue();
//                num += 5;
                x += 5;
                numLocal.set(x);
                System.out.println(Thread.currentThread().getName()+":"+numLocal.get());
            },"Thread-"+i);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

    }
}
