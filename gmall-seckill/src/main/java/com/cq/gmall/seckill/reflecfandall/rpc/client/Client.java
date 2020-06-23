package com.cq.gmall.seckill.reflecfandall.rpc.client;

import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author 彭国仁
 * @data 2020/1/7 13:08
 */
public class Client {
    //获取代表服务端接口的动态代理对象(helloService)等
    public static <T> T getRemoteProxyObj(Class serviceInterface , InetSocketAddress address) {
        Object o = Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[]{serviceInterface}, new InvocationHandler() {
            // proxy:代理的对象  method:哪个方法( sayHello(参数列表)) args:参数列表
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket();
                socket.connect(address,5);
                ObjectOutputStream  out = new ObjectOutputStream(socket.getOutputStream());
                out.writeUTF(serviceInterface.getName());
                out.writeUTF(method.getName());
                out.writeObject(method.getParameterTypes());
                out.writeObject(args);

                return null;
            }
        });

        return (T) o;
    }
}
