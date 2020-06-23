package com.cq.gmall.seckill.designmode;

/**
 * @author 彭国仁
 * @data 2019/12/19 15:44
 */
public class DependencyInversion {
    private int anInt;

    public static void main(String[] args) {
        OpenAndClose openAndClose = new OpenAndClose();
        openAndClose.open(new CDOpen());
        openAndClose.open(new CDClose());
    }
}

interface  IOpenAndClose{
    public void open(ITv tv);
}

interface ITv{
    public void play();
}

class CDOpen implements ITv {

    @Override
    public void play() {
        System.out.println("彩电打开");
    }
}

class CDClose implements ITv {

    @Override
    public void play() {
        System.out.println("彩电关闭");
    }
}

class OpenAndClose implements IOpenAndClose {
    @Override
    public void open(ITv tv) {
        tv.play();
    }
}

