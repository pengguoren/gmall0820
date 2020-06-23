package com.cq.gmall.seckill.datastruct.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author 彭国仁
 * @data 2019/12/8 10:05
 */
public class InsertSort {
    public static void main(String[] args) {
        /*int[] arr = {-8, 3, 6, 9, 7, -99, -301, 99, 8, 9, 4, 7, 5, 22, 3, 2, 6};
        sort(arr);
        System.out.println(Arrays.toString(arr));*/
        //测试一下冒泡排序的速度O(n^2), 给80000个数据，测试
        //创建要给80000个的随机的数组
        int[] arr = new int[80000];
        for(int i =0; i < 80000;i++) {
            arr[i] = (int)(Math.random() * 8000000); //生成一个[0, 8000000) 数
        }

        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        //测试冒泡排序
        sort(arr);
        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序后的时间是=" + date2Str);
    }

    public static void sort(int arr[]) {
        int temp;
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                    if (j == 0) {
                        arr[j] = temp;
                        break;
                    }
                } else {
                    arr[j + 1] = temp;
                    break;
                }
            }
        }
    }
}


