package com.cq.gmall.seckill.datastruct.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author 彭国仁
 * @data 2019/12/8 9:06
 */
public class SelectionSort {
    public static void main(String[] args) {
        /*int[] arr = {-8,3,6,9,7,-99,-301,99,8,9,4,7,5,22,3,2,6};
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
        selectSort(arr);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序后的时间是=" + date2Str);
    }

    //第一种性能差选择排序算法 需要9秒
    public static void sort(int arr[]) {
        int tem;
        for (int i = 0; i <arr.length-1; i++) {
            for (int j = i; j <arr.length - 1 ; j++) {
                if (arr[i] > arr[j + 1]) {
                    tem = arr[j + 1];
                    arr[j + 1] = arr[i];
                    arr[i] = tem;
                }
            }
        }
    }


    //第二种选择排序算法 只需2秒
    public static void selectSort(int[] arr) {


        //在推导的过程，我们发现了规律，因此，可以使用for来解决
        //选择排序时间复杂度是 O(n^2)
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) { // 说明假定的最小值，并不是最小
                    min = arr[j]; // 重置min
                    minIndex = j; // 重置minIndex
                }
            }

            // 将最小值，放在arr[0], 即交换
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }

            //System.out.println("第"+(i+1)+"轮后~~");
            //System.out.println(Arrays.toString(arr));// 1, 34, 119, 101
        }
    }
}
