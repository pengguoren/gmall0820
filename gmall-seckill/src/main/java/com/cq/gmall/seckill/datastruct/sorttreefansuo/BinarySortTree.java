package com.cq.gmall.seckill.datastruct.sorttreefansuo;

/**
 * @author 彭国仁
 * @data 2019/12/13 21:34
 */
public class BinarySortTree {
    public static void main(String[] args) {
        int arr[] = {7, 3, 10, 12, 5, 1, 9, 2, 8, 11};
        SortTree sortTree = new SortTree();
        for (int i = 0; i < arr.length; i++) {
            sortTree.add(new Node(arr[i]));
        }
        sortTree.infixOrder();
        sortTree.del(3);
        System.out.println("-------------删除后");
        sortTree.infixOrder();
    }
}

class SortTree {
    private Node root;

    //插入
    public void add(Node node) {
        if (root == null) {
            root = node;
            return;
        }
        root.add(node);
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空");
        }
    }

    //查找待删除的节点
    public Node findNode(int value) {
        if (root != null) {
            return root.findNode(value);
        }
        return null;

    }
    //查找待删除的节点的父节点

    public Node findParent(int value) {
        if (root != null) {
            return root.findParent(value);
        }
        return null;
    }

    //删除
    public void del(int value) {
        Node node = findNode(value);
        Node parent = findParent(value);
        if (node == null) {
            return;
        } else {
            //叶子节点
            if (node.left == null && node.right == null) {
                if (parent == null) {
                    this.root = null;
                } else {
                    if (parent.left != null && parent.left.no == value) {
                        parent.left = null;
                    } else if (parent.right != null && parent.right.no == value) {
                        parent.right = null;
                    }
                }
            } else if (node.left == null && node.right != null) {
                if (parent == null) {
                    this.root = node.right;
                } else {
                    if (parent.left != null && parent.left.no == value) {
                        parent.left = node.right;
                    } else if (parent.right != null && parent.right.no == value) {
                        parent.right = node.right;
                    }
                }
            } else if (node.left != null && node.right == null) {
                if (parent == null) {
                    this.root = node.left;
                } else {
                    if (parent.left != null && parent.left.no == value) {
                        parent.left = node.left;
                    } else if (parent.right != null && parent.right.no == value) {
                        parent.right = node.left;
                    }
                }
            } else {
                System.out.println("删除的是有两个子树的节点");
                //找出待删除节点右子树最小的节点
                Node minNode = node.minRightNode(node.right);
                //待删除节点右子树最小的节点的父节点
                Node minParent = findParent(minNode.no);
                if (minParent == node) {
                    if (parent == null) {
                        minNode.left = node.left;
                        root = minNode;
                    }else{
                        if (parent.left != null && parent.left.no == value) {
                            parent.left = minNode;
                            minNode.left = node.left;
                        } else if (parent.right != null && parent.right.no == value) {
                            parent.right = minNode;
                            minNode.left = node.left;
                        }
                    }
                    return;
                }
                if (parent == null) {
                    minParent.left = minNode.right;
                    minNode.left = root.left;
                    minNode.right = root.right;
                    root = minNode;
                } else {
                    minParent.left = minNode.right;
                    minNode.left = node.left;
                    minNode.right = node.right;
                    if (parent.left != null && parent.left.no == value) {
                        parent.left = minNode;
                    } else if (parent.right != null && parent.right.no == value) {
                        parent.right = minNode;
                    }
                }

            }

        }
    }
}


class Node {
    int no;
    Node left;
    Node right;

    public Node(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                '}';
    }

    //添加节点
    public void add(Node node) {
        if (node.no < this.no) {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    //查找要删除的节点
    public Node findNode(int value) {
        if (this.no == value) {
            return this;
        }
        if (this.no < value && this.right != null) {
            return this.right.findNode(value);
        }
        if (this.no > value && this.left != null) {
            return this.left.findNode(value);
        }
        return null;
    }

    //查找待删除节点的父节点
    public Node findParent(int value) {
        if (this.left != null && this.left.no == value || this.right != null && this.right.no == value) {
            return this;
        }
        if (this.no < value && this.right != null) {
            return this.right.findParent(value);
        }
        if (this.no > value && this.left != null) {
            return this.left.findParent(value);
        }
        return null;
    }

    //查找待删除节点右子树最小节点
    public Node minRightNode(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return minRightNode(node.left);
        }
    }

}