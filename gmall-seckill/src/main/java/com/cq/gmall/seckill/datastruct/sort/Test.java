package com.cq.gmall.seckill.datastruct.sort;

import org.mockito.internal.matchers.StartsWith;

import java.util.Arrays;

/**
 * @author 彭国仁
 * @data 2019/12/9 16:18
 */
public class Test {


    public static void main(String[] args) {
        String s = "sasfsdgdffghgfh";
        System.out.println(s.startsWith("sas",1));
        int a[] = {1, 2, 3, 4, 5, 6};
        int f[] = new int[2];
        int[] ints = Arrays.copyOf(a, 7);
        System.out.println(Arrays.toString(ints));
    }


   /* static int count =0;
    public static void main(String[] args) {
        int ss = ss();
        System.out.println(ss);
    }

    public static int ss() {
        count++;
        if (count == 5) {
            return 1;
        }
        ss();
        return 50;

    }*/
}
