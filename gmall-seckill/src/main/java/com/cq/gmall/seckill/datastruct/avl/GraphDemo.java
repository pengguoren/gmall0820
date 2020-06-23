package com.cq.gmall.seckill.datastruct.avl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/12/15 12:23
 */
public class GraphDemo {
    public static void main(String[] args) {
        String[] str = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(str.length);
        for (String s : str) {
            graph.insertVertex(s);
        }
        //A-B,A-C,B-C,B-D,B-E
        graph.insertGraph(0, 1, 1);
        graph.insertGraph(0, 2, 1);
        graph.insertGraph(1, 2, 1);
        graph.insertGraph(1, 3, 1);
        graph.insertGraph(1, 4, 1);
        graph.traverse();
        graph.dfs();

        System.out.println("-------------------------");
        graph.bfs();
    }
}

class Graph {
    List<String> vertexList;
    int edge;
    int[][] graph;
    //定义给数组boolean[], 记录某个结点是否被访问
    private boolean[] isVisited;


    public Graph(int n) {
        vertexList = new ArrayList<>();
        graph = new int[n][n];
    }

    public List<String> getVertex() {
        return vertexList;
    }

    public int getEdge() {
        return edge;
    }

    public int[][] getGraph() {
        return graph;
    }

    //得到第一个邻接结点的下标 w

    /**
     * @param index
     * @return 如果存在就返回对应的下标，否则返回-1
     */
    public int getFirstNeighbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (graph[index][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    //根据前一个邻接结点的下标来获取下一个邻接结点
    public int getNextNeighbor(int v1, int v2) {
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (graph[v1][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    //深度优先遍历
    //i 第一次就是 0
    private void dfs(boolean[] isVisited, int i) {
//        访问初始结点v，并标记结点v为已访问。
        System.out.print(vertexList.get(i)+"->");
        isVisited[i] = true;
//        查找结点v的第一个邻接结点w。
        int w = getFirstNeighbor(i);
//        若w存在，则继续执行4，如果w不存在，则回到第1步，将从v的下一个结点继续。
        while (w != -1) {
//        若w未被访问，对w进行深度优先遍历递归（即把w当做另一个v，然后进行步骤123）。
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
//        查找结点v的w邻接结点的下一个邻接结点，转到步骤3。
            w = getNextNeighbor(i, w);
        }
    }

    //深度遍历重载，防止出现非连通图
    public void dfs() {
        isVisited = new boolean[vertexList.size()];
        for (int j = 0; j < vertexList.size(); j++) {
            if (!isVisited[j]) {
                dfs(isVisited, j);
            }
        }
    }

    //对一个结点进行广度优先遍历的方法
    private void bfs(boolean[] isVisited, int i) {
        int u ; // 表示队列的头结点对应下标
        int w ; // 邻接结点w
        //队列，记录结点访问的顺序
        LinkedList queue = new LinkedList();
        //访问结点，输出结点信息
        System.out.print(getValueByIndex(i) + "=>");
        //标记为已访问
        isVisited[i] = true;
        //将结点加入队列
        queue.addLast(i);

        while( !queue.isEmpty()) {
            //取出队列的头结点下标
            u = (Integer)queue.removeFirst();
            //得到第一个邻接结点的下标 w
            w = getFirstNeighbor(u);
            while(w != -1) {//找到
                //是否访问过
                if(!isVisited[w]) {
                    System.out.print(getValueByIndex(w) + "=>");
                    //标记已经访问
                    isVisited[w] = true;
                    //入队
                    queue.addLast(w);
                }
                //以u为前驱点，找w后面的下一个邻结点
                w = getNextNeighbor(u, w); //体现出我们的广度优先
            }
        }

    }

    //遍历所有的结点，都进行广度优先搜索
    public void bfs() {
        isVisited = new boolean[vertexList.size()];
        for(int i = 0; i < getNumOfVertex(); i++) {
            if(!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }



    //图中常用的方法
    //返回结点的个数
    public int getNumOfVertex() {
        return vertexList.size();
    }

    //显示图对应的矩阵
    public void traverse() {
        for (int[] ints : graph) {
            System.err.println(Arrays.toString(ints));
        }
    }

    //得到边的数目
    public int getNumOfEdges() {
        return edge;
    }

    //返回结点i(下标)对应的数据 0->"A" 1->"B" 2->"C"
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    //返回v1和v2的权值
    public int getWeight(int v1, int v2) {
        return graph[v1][v2];
    }

    //插入结点
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }
    //添加边

    /**
     * @param v1     表示点的下标即使第几个顶点  "A"-"B" "A"->0 "B"->1
     * @param v2     第二个顶点对应的下标
     * @param weight 表示
     */
    public void insertGraph(int v1, int v2, int weight) {
        graph[v1][v2] = weight;
        graph[v2][v1] = weight;
        edge++;
    }
}