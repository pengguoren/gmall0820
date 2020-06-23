package com.cq.gmall.seckill.designmode.io;

import java.io.*;

/**
 * @author 彭国仁
 * @data 2019/12/26 7:58
 */
public class BfdReader {
    public static void main(String[] args) {
        Reader reader = null;
        Writer writer = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            reader = new FileReader("H:\\io\\1.txt");
            writer = new FileWriter("H:\\io\\9.txt");
            br = new BufferedReader(reader);
            bw = new BufferedWriter(writer);
            String str = "";
            while ((str = br.readLine()) != null) {
                bw.write(str+"\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
