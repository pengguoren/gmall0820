package com.cq.gmall.seckill.datastruct.algorithm;

import org.apache.tomcat.util.descriptor.web.SecurityRoleRef;

/**
 * @author 彭国仁
 * @data 2020/2/3 13:33
 */
public class Prim {
    public static void main(String[] args) {
        //1)有胜利乡有7个村庄(A,B,C,D,E,FG),现在需要修路把7个村庄连通
        //2)各个村庄的距离用边线表示(权),比如A-B距离5公里
        //3)问:如何修路保证各个村庄都能连通,并且总的修建公路总里程最短?
        int arr[][] = new int[7][7];
        for (int[] ints : arr) {
            for (int intl : ints) {
                System.out.print(intl+ "  ");
            }
            System.out.println();
        }
    }
}
