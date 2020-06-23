package com.cq.gmall.seckill.datastruct;

/**
 * @author 彭国仁
 * @data 2019/12/3 20:28
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(5);
        queue.addQueue(3);
        queue.addQueue(2);
        queue.addQueue(1);
        queue.getQueue();
        queue.showQueue();
        int head = queue.getHead();
        System.out.println("head="+head);
    }
}

class ArrayQueue {
    private int maxSize;
    private int front;
    private int rear;
    private int[] arr;


    //创建构造器
    public  ArrayQueue(int n) {
        maxSize = n;
        arr = new int[maxSize];
    }

    //查询队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    //查询队列是否已满
    public boolean isFull() {
        return front == maxSize - 1;
    }

    //添加数据到队列
    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列已满");
        }
        rear++;
        arr[rear] = n;
    }

    //获取队列的数据，出队列
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列是空的");
        }
        front++;
        return arr[front];

    }

    //显示队列的队首
    public int getHead() {
        if (isEmpty()) {
            throw new RuntimeException("队列元素为空");
        }
        return arr[++front];
    }

    //显示所有元素
    public void showQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列元素为空");
        }
        for (int i = 1; i < rear-front+1; i++) {
            System.out.println(arr[front+i]);
        }
    }
}