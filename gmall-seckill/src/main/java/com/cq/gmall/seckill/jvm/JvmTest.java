package com.cq.gmall.seckill.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 彭国仁
 * @data 2020/1/11 8:45
 */
public class JvmTest {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        int i = 1;
        boolean flag = true;
        while (flag) {
            try {
                i ++;
                list.add(new byte[1 * 1024 * 1024]);
                Thread.sleep(1000);
            } catch (Throwable e) {
                e.printStackTrace();
                flag = false;
                System.out.println(i);
            }
        }
    }
}
