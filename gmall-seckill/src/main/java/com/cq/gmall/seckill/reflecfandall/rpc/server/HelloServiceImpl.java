package com.cq.gmall.seckill.reflecfandall.rpc.server;

/**
 * @author 彭国仁
 * @data 2020/1/7 12:29
 */
public  class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        System.out.println("你好"+name);
    }
}
