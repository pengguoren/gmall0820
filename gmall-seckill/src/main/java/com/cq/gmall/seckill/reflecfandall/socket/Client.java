package com.cq.gmall.seckill.reflecfandall.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author 彭国仁
 * @data 2019/12/30 17:11
 */
public class Client {
    public static void main(String[] args) {
        for (int i =0 ;i<5;i++){
        Socket socket = new Socket();
        InputStream in = null;
        OutputStream out = null;
        try {
            socket.connect(new InetSocketAddress("127.0.0.1", 9999));
            out = socket.getOutputStream();

                File file = new File("H:\\1.jpg");
                FileInputStream fis = new FileInputStream(file);
                byte[] bytes1 = new byte[1024];
                int len = 0;
                while ((len=fis.read(bytes1)) != -1) {
                    out.write(bytes1,0,len);
                }

//            String str = "hello";
//            out.write(str.getBytes());
//            in = socket.getInputStream();
//            byte[] bytes = new byte[100];
//            in.read(bytes);
//            System.out.println(new String(bytes));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }
}
