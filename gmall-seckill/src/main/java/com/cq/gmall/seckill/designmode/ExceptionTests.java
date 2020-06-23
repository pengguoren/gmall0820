package com.cq.gmall.seckill.designmode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author 彭国仁
 * @data 2019/12/20 14:47
 */
public class ExceptionTests {
    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("d\\ssss");
            Socket socket = new Socket("127.0.0.189",88);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Socket socket = new Socket("127.0.0.1",80);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            System.out.println("---------------------------");
        }
        System.out.println("你好");
        int[] arr = {1, 2, 3,};
        System.out.println(Arrays.toString(arr));

    }
}

/*
class single {
    final   int x ;
    final  static int y ;
    static {
        y =5;
    }
    {
    }
    private single() {

        x = 10;
    }
}*/
