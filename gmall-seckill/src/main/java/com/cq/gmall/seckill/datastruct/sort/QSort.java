package com.cq.gmall.seckill.datastruct.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author 彭国仁
 * @data 2019/11/8 16:31
 */
public class QSort {
    private static int count;
    public static void main(String[] args) {
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
        sort(arr,0,arr.length-1);
        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序后的时间是=" + date2Str);
        System.out.println(count);
    }


    public static void sort(int [] arr,int low, int high) {
        count++;
        if (low>=high) {
            return;
        }
        int i =low;
        int j = high;
        int key = arr[i];
        while (i<j) {
            while(arr[j]>=key&&i<j){
                j--;
            }
            int temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
            while (arr[i]<=key&&i<j) {
                i++;
            }
            temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
        }

        //对左侧进行排序
        sort(arr,low,i-1);
        //对右侧进行排序
        sort(arr,i+1,high);
    }


}
