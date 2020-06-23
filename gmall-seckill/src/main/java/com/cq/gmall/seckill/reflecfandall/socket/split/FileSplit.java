package com.cq.gmall.seckill.reflecfandall.socket.split;

import java.io.*;

/**
 * @author 彭国仁
 * @data 2019/12/31 8:14
 */
public class FileSplit {
    public static void main(String[] args) {
        OutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("H:\\code.rar");
            byte[] buf = new byte[1024 * 1024];
            int len = 0;
            int count = 1;
            while ((len = fis.read(buf)) != -1) {
                fos = new FileOutputStream(new File("H:\\io",count++ +".part"));
                fos.write(buf,0,len);
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
