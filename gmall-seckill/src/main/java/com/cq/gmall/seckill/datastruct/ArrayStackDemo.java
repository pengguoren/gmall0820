package com.cq.gmall.seckill.datastruct;


import java.util.Scanner;

/**
 * @author 彭国仁
 * @data 2019/12/6 7:13
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        System.out.println("栈的操作");
//        ArrayStack stack = new ArrayStack(4);
        LinkedStack stack = new LinkedStack(4);
        String key = "";
        boolean flag = true;
        while (flag) {
            System.out.println("push：入栈");
            System.out.println("pop：出栈");
            System.out.println("show：遍历");
            System.out.println("exit：退出");
            Scanner scanner = new Scanner(System.in);
            key = scanner.next();
            switch (key) {
                case "push":
                    System.out.println("请输入int：");
                    int x = Integer.parseInt(scanner.next());
                    stack.push(x);
                    break;
                case "pop":
                    try {
                        Node pop = stack.pop();
                        System.out.println("出栈的是："+pop);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "show":
                    stack.traverseStack();
                    break;
                case "exit":
                    scanner.close();
                    flag = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("退出程序");
    }
}

class ArrayStack {
    private int top = -1;
    private int stack[];
    private int maxSize;

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    //入栈
    public void push(int m) {
        if (top == maxSize-1) {
            System.out.println("栈满~~~");
            return;
        }
        if (top < maxSize) {
            stack[++top] = m;
        }
    }

    //出栈
    public int pop() {
        if (top == -1) {
            throw new RuntimeException("栈空，没有数据可以取出");
        }
        return stack[top--];
    }

    //打印栈
    public void traverseStack() {
        if (top == -1) {
            System.out.println("栈空");
        }
        for (int i = top; i >= 0; i--) {
            System.out.println("stack["+i+"]="+stack[i]);
        }
    }
}

class LinkedStack{
    private Node head = new Node(0);
    private int maxSize;
    private int count ;

    public LinkedStack(int maxSize) {
        this.maxSize = maxSize;
    }

    //入栈
    public void push(int m) {
        Node temp = head;
        if (count+1 > maxSize) {
            System.out.println("栈已满，无法加入");
            return;
        }
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node(m);
        count++;
    }

    //出栈
    public Node pop() {
        Node temp0 = head;
        Node temp1 = head.next;
        if (count-1<0) {
            throw new RuntimeException("栈空，无法取出");
        }
        while (temp1.next != null) {
            temp0 = temp0.next;
            temp1 = temp1.next;
        }
        temp0.next = null;
        count--;
        return temp1;
    }

    //遍历
    public void traverseStack() {
        if (count-1<0) {
            System.out.println("栈空");
            return;
        }
        for (int i = count; i >0 ; i--) {
            Node temp0 = head;
            for (int j = 0; j <i ; j++) {
                temp0 = temp0.next;
            }
            System.out.println("stack["+(count-i)+"]="+temp0);
        }
    }

}
//思路：创建一个节点类，创建一个单向链表，设置最大长度，头节点和count计数器记录栈中数据的个数
class Node{
    public int no;
    public Node next;

    public Node(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                '}';
    }
}