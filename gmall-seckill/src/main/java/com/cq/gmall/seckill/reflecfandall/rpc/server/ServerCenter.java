package com.cq.gmall.seckill.reflecfandall.rpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 彭国仁
 * @data 2020/1/7 12:39
 */
public class ServerCenter implements Server {
    //map:服务端的所有可供客户端访问的接口,都注册到该map中
    //key:接口的名字"He1loservice" value:真正的He1loservice实现

    private static Map<String, Class> serverRegister = new HashMap();
    //开启服务

    ServerSocket serverSocket = null;

    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(6000));
            Socket socket = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            //因为 ObjectInputstream对发送数据的顺序严格要求,
            String serviceName = in.readUTF();
            String methodName = in.readUTF();
            Class[] parameterTypes = null;
            Class[] args = null;
            try {
                parameterTypes = (Class[]) in.readObject();
                args = (Class[]) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //根据接口名找接口
            Class serviceClass = serverRegister.get(serviceName);
            for (Class parameterType : parameterTypes) {
                try {
                   Method method =  serviceClass.getDeclaredMethod(methodName, parameterType);
//                    method.invoke(serviceClass.newInstance(),)
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(Class serviceInterface, Class serviceImpl) {
        serverRegister.put(serviceInterface.getName(), serviceImpl);
    }

}
