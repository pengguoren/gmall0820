package com.cq.gmall.seckill.datastruct;

import java.text.BreakIterator;

/**
 * @author 彭国仁
 * @data 2019/12/5 16:02
 */
public class CircleSingleLinkedListDemo {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(40);
        CircleSingleLinkedList.traverse(circleSingleLinkedList);
        circleSingleLinkedList.countBoy(1,2,15);

    }
}

class CircleSingleLinkedList{
    private static Boy first = null;

    public  void addBoy(int nums) {
        if (nums < 2) {
            System.out.println("环形链表数据需要大于等于二");
            return;
        }
        //辅助指针帮助创建环形列表
        Boy curBoy = null;
        for (int i = 1; i <=nums ; i++) {
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                first.setBoy(first);
                curBoy = first;
            } else {
                curBoy.setBoy(boy);
                boy.setBoy(first);
                curBoy = boy;
            }
        }
    }
    //遍历环形链表
    public static void traverse(CircleSingleLinkedList cs){
        if (first == null) {
            System.out.println("空链表");
            return;
        }
        Boy curBoy = first;
        while (true) {
            System.out.println(curBoy);
            if (curBoy.getBoy() == first) {
                break;
            }
            curBoy = curBoy.getBoy();
        }
    }

    //根据用户的输入，计算出小孩出圈的顺序

    /**
     *
     * @param startNo 表示从第几个小孩开始数数
     * @param countNum 表示数几下
     * @param nums 表示开始有多少小孩在圈中
     */
    public void countBoy(int startNo,int countNum,int nums) {
        if(first == null||startNo<1||startNo>nums){
            System.out.println("参数输入有误请重新输入");
            return;
        }
        Boy helper = first;
        //先把helper移动到链表的末尾
        while (true) {
            if (helper.getBoy() == first) {
                break;
            }
            helper = helper.getBoy();
        }
        //从第几个开始数，把helper和first 一起移动startNo -1个位置
        for (int i = 0; i < startNo-1; i++) {
            helper = helper.getBoy();
            first = first.getBoy();
        }
        //数几次，移动first和helper count - 1个位置
        while (true) {
            for (int i = 0; i <countNum-1 ; i++) {
                helper = helper.getBoy();
                first = first.getBoy();
            }
            System.out.println("出圈:"+first.getNo());
            //出圈
            first = first.getBoy();
            helper.setBoy(first);

            if (first == helper) {
                System.out.println("最后一个出圈："+first.getNo());
                break;
            }
        }

    }
}
class Boy{
    private int no;
    private Boy boy;

    @Override
    public String toString() {
        return "Boy{" +
                "no=" + no +
                '}';
    }

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getBoy() {
        return boy;
    }

    public void setBoy(Boy boy) {
        this.boy = boy;
    }
}