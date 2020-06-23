package com.cq.gmall.seckill.datastruct;

/**
 * @author 彭国仁
 * @data 2019/12/4 14:35
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(2, "李逵", "黑子");
        HeroNode node3 = new HeroNode(3, "林冲", "豹子头");
        HeroNode node4 = new HeroNode(4, "时迁", "鼓上蚤");
        HeroNode node5 = new HeroNode(5, "卢俊义", "玉麒麟");
        HeroNode node6 = new HeroNode(6, "aaa", "aaa");
        HeroNode node7 = new HeroNode(7, "bbb", "bbb");
        HeroNode node8 = new HeroNode(8, "ccc", "ccc");
        HeroNode node9 = new HeroNode(9, "dddd", "ddd");
        SingleLikedList singleLikedList = new SingleLikedList();
        SingleLikedList singleLikedList1 = new SingleLikedList();
        try {
            singleLikedList.addSpecilNode(node3);
            singleLikedList.addSpecilNode(node1);
            singleLikedList.addSpecilNode(node8);
            singleLikedList.addSpecilNode(node7);
            singleLikedList1.addSpecilNode(node2);
            singleLikedList1.addSpecilNode(node5);
            singleLikedList1.addSpecilNode(node6);
            singleLikedList1.addSpecilNode(node9);
            singleLikedList1.addSpecilNode(node4);

//            singleLikedList.updateNode(node5);
//            singleLikedList.deleteNode(7);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int count = 0;
        HeroNode temp = singleLikedList.getHead();
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        System.out.println("该节点的节点数为："+count);

        singleLikedList.traversalNode();
        System.out.println("---------------------------------------------");
        singleLikedList1.traversalNode();
        //将两个有序链表合并成一个有序链表
        SingleLikedList s3 = SingleLikedList.concatLinkedList(singleLikedList1, singleLikedList);
        System.out.println("合并后的链表");
        s3.traversalNode();
        //节点反转
        SingleLikedList.inversionNode(s3);
        System.out.println("节点反转-------------------");
        s3.traversalNode();

    }
}

//链表
class SingleLikedList {
    private HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return head;
    }

    //插入指定位置节点
    public void addSpecilNode(HeroNode node) {
        int no = node.no;
        HeroNode temp = head;
        while (temp.next != null) {
            if (temp.next.no == no) {
                throw new RuntimeException("该排名已存在，添加失败");
            } else if (temp.next.no > no) {
                node.next = temp.next;
                temp.next = node;
                return;
            }
            temp = temp.next;
        }
        temp.next = node;
    }

    //插入节点
    public void addNode(HeroNode node) {
        HeroNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
    }

    //修改节点
    public void updateNode(HeroNode node) {
        HeroNode temp = head;
        while (temp.next != null) {
            if (temp.next.no == node.no) {
                temp.next.name = node.name;
                temp.next.nickname = node.nickname;
                return;
            }
            temp = temp.next;
        }
        addSpecilNode(node);
    }

    //删除节点
    public void deleteNode(int no) {
        HeroNode temp = head;
        while (temp.next != null) {
            if (temp.next.no == no) {
                temp.next = temp.next.next;
                return;
            }
            temp = temp.next;
        }
        System.out.println("要删除的节点不存在");
    }

    //遍历节点
    public void traversalNode() {
        HeroNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
            System.out.println(temp);
        }
    }

    //节点反转
    public static void inversionNode(SingleLikedList singleLikedList1) {
        //创建一个新的节点
        SingleLikedList singleLikedList = new SingleLikedList();
        HeroNode head1 = singleLikedList.getHead();
        //遍历链表进行节点转换
        HeroNode head = singleLikedList1.getHead();
        while (head.next != null) {
            HeroNode temp = head;
            temp = temp.next;
            singleLikedList1.deleteNode(temp.no);
            temp.next = singleLikedList.head.next;
            singleLikedList.head.next = temp;
        }
        singleLikedList1.head.next = singleLikedList.head.next;
    }

    //将两个有序链表合并成一个有序链表
    public static SingleLikedList concatLinkedList(SingleLikedList s1, SingleLikedList s2) {
        SingleLikedList s3 = new SingleLikedList();
        HeroNode head = s3.head;
        HeroNode temp = null;
        HeroNode temp1 = s1.head;
        HeroNode temp2 = s2.head;
        while (temp1.next != null) {
            temp = temp1.next;
            temp1.next = temp1.next.next;
            temp.next = null;
            s3.addSpecilNode(temp);
        }
        while (temp2.next != null) {
            temp = temp2.next;
            temp2.next = temp2.next.next;
            temp.next = null;
            s3.addSpecilNode(temp);
        }
        return s3;
    }

    //遍历单个插入
    public void adddd(HeroNode temp1,HeroNode temp2) {

    }
}
class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;

    public HeroNode(int no,String name,String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
