package com.cq.gmall.seckill.datastruct;

import tk.mybatis.mapper.generator.FalseMethodPlugin;

import java.util.Random;

/**
 * @author 彭国仁
 * @data 2019/12/7 11:36
 */
public class MazeBack {
    public static void main(String[] args) {
        int maze[][] = new int[8][7];
        for (int i = 0; i < 7; i++) {
            maze[0][i] = 1;
            maze[7][i] = 1;
        }
        for (int i = 0; i < 8; i++) {
            maze[i][0] = 1;
            maze[i][6] = 1;
        }
        maze[3][1] = 1;
        maze[3][2] = 1;
        back(maze, 1, 1);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(maze[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static boolean back(int[][] maze, int i, int j) {
        if (maze[6][5] == 2) {
            return true;
        }
        if (maze[i][j] == 0) {
            maze[i][j] = 2;
            int num = new Random().nextInt(4);
            if (num == 0) {
                //下 右 上 左
                if (back(maze, i + 1, j)) {
                    return true;
                } else if (back(maze, i, j + 1)) {
                    return true;
                } else if (back(maze, i - 1, j)) {
                    return true;
                } else if (back(maze, i, j - 1)) {
                    return true;
                } else {
                    maze[i][j] = 3;
                    return false;
                }
            } else if (num == 1) {
                //右 上 左 下
                if (back(maze, i, j + 1)) {
                    return true;
                } else if (back(maze, i - 1, j)) {
                    return true;
                } else if (back(maze, i, j - 1)) {
                    return true;
                } else if (back(maze, i + 1, j)) {
                    return true;
                } else {
                    maze[i][j] = 3;
                    return false;
                }
            } else if (num == 2) {

                // 上 左 下 右
                if (back(maze, i - 1, j)) {
                    return true;
                } else if (back(maze, i, j - 1)) {
                    return true;
                } else if (back(maze, i + 1, j)) {
                    return true;
                } else if (back(maze, i, j + 1)) {
                    return true;
                } else {
                    maze[i][j] = 3;
                    return false;
                }
            } else if (num == 3) {
                //  左 下 右 上
                if (back(maze, i, j - 1)) {
                    return true;
                } else if (back(maze, i + 1, j)) {
                    return true;
                } else if (back(maze, i, j + 1)) {
                    return true;
                } else if (back(maze, i - 1, j)) {
                    return true;
                } else {
                    maze[i][j] = 3;
                    return false;
                }
            }
        }
        return false;
    }
}
