package com.cq.gmall.seckill.designmode.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author 彭国仁
 * @data 2019/12/25 20:28
 */
public class IOReaderW {
    public static void main(String[] args) {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            fr = new FileReader("H:\\io\\1.txt");
            fw = new FileWriter("H:\\io\\5.txt");
            char[] chars = new char[100];
            int n = 0;
            while ((n = fr.read(chars)) != -1) {
                String str = new String(chars, 0, n);
                System.out.println(str);
                fw.write(chars,0,n);
//                System.out.println(chars);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
