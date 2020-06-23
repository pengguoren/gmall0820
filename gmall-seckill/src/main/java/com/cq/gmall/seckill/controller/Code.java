package com.cq.gmall.seckill.controller;

import java.util.*;
import java.util.function.BiPredicate;

/**
 * @author 彭国仁
 * @data 2019/11/13 18:54
 */
public class Code {
    public static void main(String[] args) {

        //2.怎样用map和 reduce方法数一数流中有多少个 Employee呢?
      /*  List<Integer> emps = new ArrayList();
        Optional<Integer> count = emps.stream()
        .map((e)->1)
        .reduce(Integer::sum);
        System. out. println(count.get());*/

        //1.给定一个数字列表,如何返回一个由每个数的平方构成的列表呢?
        //给定【1,2,3,4,5】,应该返回【1,4,9,16,25】
      /*  List<Integer> numlist = Arrays.asList(1, 2, 3, 4, 5);
        numlist.stream()
                .map((x) -> x * x)
                .forEach(System.out::println);
*/
      /*  BiPredicate<String, String> biPredicate = String::equals;
        boolean test = biPredicate.test("ssd", "ssd");
        System.out.println(test);*/


       /* Comparator<Integer> com = Integer::compare;
        int compare = com.compare(3, 5);
        System.out.println(compare);*/

        /*//n步台阶的走法，每次走一步或者两步。
        Integer sum = sum(1);
        System.out.println(sum);*/


      /*  List<String> code = new ArrayList<>(20);
        code.add("a");
        code.remove("a");
        int [] a1 = {1,2,3,4,5};
        int [] a2 = a1.clone();
        int[] a3 = new int[10];

        System.arraycopy(a1,1,a3,1,4);
        int[] a4 = Arrays.copyOf(a1, 3);
        int[] a5 = Arrays.copyOfRange(a1, 0,1);
        System.out.println(Arrays.toString(a2));
        System.out.println(Arrays.toString(a3));
        System.out.println(Arrays.toString(a4));
        System.out.println(Arrays.toString(a5));
        String n = new String("sddfd");
        n.length();*/


    }

    private static Integer sum(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        return sum(n - 2) + sum(n-1);
    }
}
