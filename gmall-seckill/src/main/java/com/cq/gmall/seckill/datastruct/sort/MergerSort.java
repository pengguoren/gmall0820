package com.cq.gmall.seckill.datastruct.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author 彭国仁
 * @data 2019/12/8 22:37
 */
public class MergerSort {
    public static void main(String[] args) {
       /* int arr[] = { 1,2, 5, 6, 7, 4, 3, 9};
        int temp[]=new int[arr.length];
        mergeSort(arr,0,arr.length-1,temp);
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
        int temp[]=new int[arr.length];
        mergeSort(arr,0,arr.length-1,temp);
        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序后的时间是=" + date2Str);
    }

    //分
    public static void mergeSort(int arr[], int left, int right, int temp[]) {
        if (left<right) {
            int mid = (left + right) / 2;
            //向做递归进行分解
            mergeSort(arr,left,mid,temp);
            //向右递归进行分解
            mergeSort(arr, mid + 1, right, temp);
            merge(arr,left,mid,right,temp);
        }

    }

    //合
    public static void merge(int arr[], int left, int mid, int right, int temp[]) {
        //左边有序序列的初始化索引
        int i = left;
        //右边有序序列的初始化索引
        int j =mid+1;
        int t = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                t += 1;
                i += 1;
            }else {
                temp[t] = arr[j];
                t += 1;
                j += 1;
            }
        }
        while (i<=mid) {
            temp[t] = arr[i];
            t += 1;
            i += 1;
        }
        while (j<=right) {
            temp[t] = arr[j];
            t += 1;
            j += 1;
        }
        //将temp复制给arr数组
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            t+=1;
            tempLeft += 1;
        }
    }
}
