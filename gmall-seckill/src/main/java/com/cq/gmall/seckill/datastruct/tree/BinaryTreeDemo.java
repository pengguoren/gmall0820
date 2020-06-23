package com.cq.gmall.seckill.datastruct.tree;

/**
 * @author 彭国仁
 * @data 2019/12/10 17:37
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        //先需要创建一颗二叉树
        BinaryTree binaryTree = new BinaryTree();
        //创建需要的结点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");
        //说明，我们先手动创建该二叉树，后面我们学习递归的方式创建二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        binaryTree.setRoot(root);

     /*   System.out.println("前序" + binaryTree.preSelect(3));
        System.out.println("中序" + binaryTree.midSelect(3));
        System.out.println("后序" + binaryTree.endSelect(3));*/
//        System.out.println("后序遍历");
//        binaryTree.postOrder2(); // 2,5,4,3,1

        binaryTree.del(1);
        System.out.println("前序遍历"); // 1,2,3,5,4
        binaryTree.preOrder();

        System.out.println("中序遍历");
        binaryTree.infixOrder(); // 2,1,5,3,4

        System.out.println("后序遍历");
        binaryTree.postOrder(); // 2,5,4,3,1
    }
}

class BinaryTree {
    private HeroNode root;

    public HeroNode getRoot() {
        return root;
    }

    public void setRoot(HeroNode root) {
        this.root = root;
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
    //后序遍历2
    public void postOrder2() {
        if (this.root != null) {
            this.root.endOrder2();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //前序查找
    public HeroNode preSelect(int no) {
        if (root != null) {
            return root.preSelect(no);
        } else {
            return null;
        }
    }

    //中序查找
    public HeroNode midSelect(int no) {
        if (root != null) {
            return root.midSelect(no);
        } else {
            return null;
        }
    }

    //后序查找
    public HeroNode endSelect(int no) {
        if (root != null) {
            return root.endSelect(no);
        } else {
            return null;
        }
    }

    //递归删除
    //如果是叶子节点，就删除叶子节点
    //如果删除的节点是非叶子节点，就删除该子树
    public void del(int no) {
        if(root != null) {
            //如果只有一个root结点, 这里立即判断root是不是就是要删除结点
            if(root.getNo() == no) {
                root = null;
            } else {
                //递归删除
                root.del(no);
            }
        }else{
            System.out.println("空树，不能删除~");
        }
    }
}

class HeroNode {
    private int no;
    private String name;
    private HeroNode left;
    private HeroNode right;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
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
    //后序遍历2
    public void endOrder2() {
        if (this.right != null) {
            this.right.endOrder2();
        }
        if (this.left != null) {
            this.left.endOrder2();
        }
        System.out.println(this);
    }

    //前序查找
    public HeroNode preSelect(int no) {
        System.out.println(this);
        if (this.no == no) {
            return this;
        }
        //1.则判断当前结点的左子节点是否为空，如果不为空，则递归前序查找
        //2.如果左递归前序查找，找到结点，则返回
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.preSelect(no);
        }
        if (resNode != null) {//说明我们左子树找到
            return resNode;
        }
        //1.左递归前序查找，找到结点，则返回，否继续判断，
        //2.当前的结点的右子节点是否为空，如果不空，则继续向右递归前序查找
        if (this.right != null) {
            resNode = this.right.preSelect(no);
        }
        return resNode;
    }

    //中序查找
    public HeroNode midSelect(int no) {
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.midSelect(no);
        }
        if (resNode != null) {//说明我们左子树找到
            return resNode;
        }
        System.out.println(this);
        if (this.no == no) {
            return this;
        }
        if (this.right != null) {
            resNode = this.right.midSelect(no);
        }
        return resNode;
    }

    //后序查找
    public HeroNode endSelect(int no) {
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.endSelect(no);
        }
        if (resNode != null) {//说明我们左子树找到
            return resNode;
        }
        if (this.right != null) {
            resNode = this.right.endSelect(no);
        }
        if(resNode != null) {
            return resNode;
        }
        System.out.println(this);
        if (this.no == no) {
            return this;
        }
        return resNode;
    }

    public void del(int no) {
        if (this.left != null) {
            if (this.left.no == no) {
                this.left= null;
                return;
            }
            this.left.del(no);
        }
        if (this.right != null) {
            if (this.right.no == no) {
                this.right= null;
                return;
            }
            this.right.del(no);
        }
    }
}
