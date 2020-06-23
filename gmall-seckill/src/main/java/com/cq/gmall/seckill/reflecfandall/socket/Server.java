package com.cq.gmall.seckill.reflecfandall.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 彭国仁
 * @data 2019/12/30 17:42
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(9999);
            while (true) {
                socket = server.accept();
                new Thread(new MyUpload(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (server != null) {
                    server.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}