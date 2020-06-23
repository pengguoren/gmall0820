package com.cq.gmall.seckill.designmode.prototype;

/**
 * @author 彭国仁
 * @data 2019/12/21 13:15
 */
public class DeepCloneTarget implements Cloneable {
    private String name;
    private int age;

    public DeepCloneTarget(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "DeepCloneTarget{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

