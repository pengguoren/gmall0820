package com.cq.gmall.seckill.designmode.io;

/**
 * @author 彭国仁
 * @data 2019/12/26 9:03
 */
public class testsss {
    public static void main(String[] args) throws InterruptedException {
        Duck duck = new Duck();
        duck.fast_fly();
        duck.rotate_fly();//接口中的默认方法已经被覆盖
        Fly.slow_fly();//调用接口中的静态方法和调用普通类的静态方法一样 接口类.方法名()
        K k = new K();
        k.s();
    }
}

interface Fly {
    default void fast_fly(){
        System.out.println("快速的飞。。。");
    }
    default void rotate_fly(){
        System.out.println("旋转的飞。。。");
    }
    static void slow_fly(){
        System.out.println("慢慢的飞。。。");
    }
}
class Duck implements Fly {
    @Override
    public void rotate_fly() {
        System.out.println("我其实不会旋转的飞。。。");
    }
}

interface C{
    static void s(){
        System.out.println("我锤子");
    }
}
interface D{
    default void s(){
        System.out.println("我晕了");
    }
}

class K implements C, D {
}
