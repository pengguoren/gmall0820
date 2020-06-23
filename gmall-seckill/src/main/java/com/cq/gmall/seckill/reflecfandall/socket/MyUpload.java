package com.cq.gmall.seckill.reflecfandall.socket;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author 彭国仁
 * @data 2019/12/30 21:15
 */
public class MyUpload implements Runnable {

    private Socket socket;

    public MyUpload(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        FileOutputStream fos = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            System.out.println("连接成功");
            System.out.println(Thread.currentThread().getName()+"线程名称");
            in = socket.getInputStream();
//            byte[] bytes = new byte[100];
//            in.read(bytes);
//            String s = new String(bytes);
//            System.out.println(s);
//            out = socket.getOutputStream();
//
//            String str = "你好";
//            out.write(str.getBytes());
            fos = new FileOutputStream("H:\\4.jpg");
            byte[] bytes1 = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes1)) != -1) {
                fos.write(bytes1, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
