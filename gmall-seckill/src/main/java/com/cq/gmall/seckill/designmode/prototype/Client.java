package com.cq.gmall.seckill.designmode.prototype;

/**
 * @author 彭国仁
 * @data 2019/12/21 10:04
 */
public class Client {
    public static void main(String[] args) throws Exception {
        DeepPrototype p = new DeepPrototype("大牛","广州");
        p.setDeepCloneTarget(new DeepCloneTarget("山",100));
        DeepPrototype p1 = (DeepPrototype)p.clone();
        System.out.println(p1.getDeepCloneTarget());
        DeepCloneTarget deepCloneTarget = p.getDeepCloneTarget();

        deepCloneTarget.setAge(200);
        System.out.println(p1.getDeepCloneTarget());
       /* Sheep sheep = new Sheep("小白", 1);
        Object o = new Object();
        Sheep sheep1 = null;
        Sheep sheep2 = null;
        Sheep sheep3 = null;
        Sheep sheep4 = null;
        sheep1 = (Sheep) sheep.clone();
        sheep2 = (Sheep) sheep.clone();
        sheep3 = (Sheep) sheep.clone();
        sheep4 = (Sheep) sheep.clone();
        System.out.println(sheep1);
        System.out.println(sheep2);
        System.out.println(sheep3);
        System.out.println(sheep4);*/
    }
}
