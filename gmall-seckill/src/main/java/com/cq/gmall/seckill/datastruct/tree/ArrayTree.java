package com.cq.gmall.seckill.datastruct.tree;

/**
 * @author 彭国仁
 * @data 2019/12/11 8:58
 */
public class ArrayTree {
    public static void main(String[] args) {
        int arr[] = {9, 2, 6, 4, 8, 1, 7};
        Tree tree = new Tree(arr);
        tree.toTree();
       // tree.preOrder();
        //tree.infixOrder();
        tree.postOrder();
    }

}
/**
 * 数组转顺序存储二叉树
 */
class Tree {
    private int arr[];
    private Node root;

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Tree(int arr[]) {
        this.arr = arr;
    }

    //前序遍历
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.root != null) {
            this.root.midOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //后序遍历
    public void postOrder() {
        if (this.root != null) {
            this.root.endOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }
    //数组转化为树
    public void toTree() {
        this.root = new Node(arr[0]);
        int n = 0;
        this.root.toTree(arr,n);
    }
}

class Node {
    private int no;
    private Node left;
    private Node right;

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                '}';
    }

    public Node(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    //中序遍历
    public void midOrder() {

        if (this.left != null) {
            this.left.midOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.midOrder();
        }
    }

    //后序遍历
    public void endOrder() {

        if (this.left != null) {
            this.left.endOrder();
        }
        if (this.right != null) {
            this.right.endOrder();
        }
        System.out.println(this);
    }

    //
    public void toTree(int[] arr,int n) {
        if (2 * n + 1 < arr.length) {
            this.left=new Node(arr[2 * n + 1]);
            this.right=new Node(arr[2 * n + 2]);
            this.left.toTree(arr,2 * n + 1);
            this.right.toTree(arr,2 * n + 2);
        }
    }
}