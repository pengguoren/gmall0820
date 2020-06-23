package com.cq.gmall.seckill.designmode.io;

import java.io.*;

/**
 * @author 彭国仁
 * @data 2019/12/25 19:07
 */
public class IOText {
    public static void main(String[] args) {
       /* File file = new File("H:\\io\\1.txt");
        File fileo = new File("H:\\io\\3.txt");
        FileInputStream fis = null;
        FileOutputStream fos = null;
        byte[] bytes = new byte[1024];
        int n = 0;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(fileo);
            while ((n = fis.read(bytes) )!= -1) {
                fos.write(bytes,0,n);
                String str = new String(bytes,0,n);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        /*if (file.exists()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                System.out.println(file1);
            }
        }*/
    }
}
interface A{
    void method();

}
abstract class Father{
   abstract void function();
}

class  B extends Father implements A{

    @Override
    public void method() {

    }

    @Override
    void function() {

    }
}