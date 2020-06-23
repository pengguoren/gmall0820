package com.cq.gmall.seckill.datastruct;

import javax.validation.constraints.Size;
import java.util.Scanner;

/**
 * @author 彭国仁
 * @data 2019/12/4 8:40
 */
public class CircleArrayDemo {

    public static void main(String[] args) {
        //测试一把
        System.out.println("测试数组模拟环形队列的案例~~~");
        // 创建一个环形队列
        CircleArray queue = new CircleArray(4); //说明设置4, 其队列的有效数据最大是3
        char key = ' '; // 接收用户输入
        Scanner scanner = new Scanner(System.in);//
        boolean loop = true;
        // 输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);// 接收一个字符
            switch (key) {
                case 's':
                    try {
                        queue.showQueue();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'a':
                    System.out.println("输入一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g': // 取出数据
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h': // 查看队列头的数据
                    try {
                        int res = queue.getHead();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e': // 退出
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~");
    }

}

class CircleArray{
    private int maxSize;
    private int front;
    private int rear;
    private int[] arr;

    //创建构造器
    public  CircleArray(int n) {
        maxSize = n;
        arr = new int[maxSize];
    }

    //查询队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    //查询队列是否已满
    public boolean isFull() {
        return (rear+1)%maxSize == front;
    }

    //添加数据到队列
    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列已满");
        }
        arr[rear] = n;
        rear = (rear + 1)%maxSize;
    }

    //获取队列的数据，出队列
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列是空的");
        }
        int val = arr[front];
        front = (front + 1)%maxSize;
        return val;
    }

    //显示队列的队首
    public int getHead() {
        if (isEmpty()) {
            throw new RuntimeException("队列元素为空");
        }
        return arr[front];
    }

    //显示所有元素
    public void showQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列元素为空");
        }
        for (int i = front; i < front + size(); i++) {
            System.out.println(arr[i%maxSize]);
        }
    }

    public int size() {
        return (rear + maxSize - front) % maxSize;
    }
}