package com.cq.gmall.seckill.datastruct;

/**
 * @author 彭国仁
 * @data 2019/12/5 9:11
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode2 node1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 node2 = new HeroNode2(2, "李逵", "黑子");
        HeroNode2 node3 = new HeroNode2(3, "林冲", "豹子头");
        HeroNode2 node4 = new HeroNode2(4, "时迁", "鼓上蚤");
        HeroNode2 node5 = new HeroNode2(5, "卢俊义", "玉麒麟");
        DoubleLinkedList dl = new DoubleLinkedList();
        dl.addNode(node1);
        dl.addNode(node2);
        dl.addNode(node3);
        dl.addNode(node4);
        dl.addNode(node5);
        dl.traversalNode();
        //修改
        HeroNode2 node6 = new HeroNode2(5, "武松", "打虎");
        dl.updateNode(node6);
        System.out.println("------------------------");
        dl.traversalNode();
        //删除
        dl.deleteNode(5);
        System.out.println("------------------------");
        dl.traversalNode();

    }


}
class DoubleLinkedList{
    private HeroNode2 head = new HeroNode2(0,"","");

    public HeroNode2 getHead() {
        return head;
    }

    //插入节点
    public void addNode(HeroNode2 node) {
        HeroNode2 temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
        node.pre = temp;
    }

    //修改节点
    public void updateNode(HeroNode2 node) {
        HeroNode2 temp = head;
        while (temp.next != null) {
            if (temp.next.no == node.no) {
                temp.next.name = node.name;
                temp.next.nickname = node.nickname;
                return;
            }
            temp = temp.next;
        }
        System.out.println("该节点不存在，不能修改");
    }

    //删除节点
    public void deleteNode(int no) {
        HeroNode2 temp = head.next;
        while (temp != null) {
            if (temp.no == no) {
                   temp.pre.next = temp.next;
                if (temp.next != null) {
                    temp.next.pre = temp.pre;
                }
                return;
            }
            temp = temp.next;
        }
        System.out.println("要删除的节点不存在");
    }

    //遍历节点
    public void traversalNode() {
        HeroNode2 temp = head;
        while (temp.next != null) {
            temp = temp.next;
            System.out.println(temp);
        }
    }

}


class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next;
    public HeroNode2 pre;

    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}