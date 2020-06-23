package com.cq.gmall.seckill.reflecfandall.rpc.server;

/**
 * @author 彭国仁
 * @data 2020/1/7 12:36
 */

public interface Server {
     void start();
     void stop();
//    注册服务

    /**
     *
     * @param serviceInterface d发士大夫
     * @param serviceImpl s三十东方
     */
     void register(Class serviceInterface,Class serviceImpl);
}
