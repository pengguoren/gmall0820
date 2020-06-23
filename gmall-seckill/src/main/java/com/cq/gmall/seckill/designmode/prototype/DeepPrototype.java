package com.cq.gmall.seckill.designmode.prototype;

/**
 * @author 彭国仁
 * @data 2019/12/21 13:16
 */
public class DeepPrototype implements Cloneable {
    private String name;
    private String address;
    private DeepCloneTarget deepCloneTarget;

    public DeepCloneTarget getDeepCloneTarget() {
        return deepCloneTarget;
    }

    public void setDeepCloneTarget(DeepCloneTarget deepCloneTarget) {
        this.deepCloneTarget = deepCloneTarget;
    }

    public DeepPrototype(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "DeepPrototype{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public DeepPrototype clone() throws CloneNotSupportedException {
        DeepPrototype deepPrototype= (DeepPrototype)super.clone();
        deepPrototype.deepCloneTarget = (DeepCloneTarget)deepCloneTarget.clone();
        return deepPrototype;
    }
}
