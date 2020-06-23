package com.cq.gmall.seckill.datastruct.algorithm;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
/**
 * @author 彭国仁
 * @data 2020/2/2 20:31
 */
public class Greedy {
    public static void main(String[] args) {
        Map<String,List<String>> map = new HashMap<>();
//        List<String> list1 = Arrays.asList("北京", "上海", "天津"); 比下面的写法简单
        map.put("k1",Arrays.asList(new String[]{"北京","上海", "天津"}));
        map.put("k2",Arrays.asList(new String[]{"广州","北京", "深圳"}));
        map.put("k3",Arrays.asList(new String[]{"成都","上海", "杭州"}));
        map.put("k4",Arrays.asList(new String[]{"上海", "天津"}));
        map.put("k5",Arrays.asList(new String[]{"杭州","大连"}));
        //存储无重复的所有城市
        Set<String> address = new HashSet<>();
        //遍历所有的广播
        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        Iterator<Map.Entry<String, List<String>>> iterator = entries.iterator();
        //无重复放入所有的城市
        while (iterator.hasNext()) {
            List<String> value = iterator.next().getValue();
            for (String s : value) {
                address.add(s);
                System.out.print(s+" ");
            }
            System.out.println();
        }
        //城市没被覆盖完时循环
        List<String> list = new ArrayList<>();
        while (address.size() > 0) {
            iterator = entries.iterator();
            Map<String, Integer> map1 = new HashMap<>();
            //查询出每个广播能够覆盖的区域数量
            while (iterator.hasNext()) {
                int l = 0;
                Map.Entry<String, List<String>> next = iterator.next();
                String key = next.getKey();
                List<String> listV = next.getValue();
                for (String s : listV) {
                    if (address.contains(s)) {
                        l++;
                    }
                }
                map1.put(key,l);
            }
            Set<Map.Entry<String, Integer>> entries1 = map1.entrySet();
            Iterator<Map.Entry<String, Integer>> iterator1 = entries1.iterator();
            int max = 0;
            String key = "";
            while (iterator1.hasNext()) {
                Map.Entry<String, Integer> next = iterator1.next();
                Integer value = next.getValue();
                if (value >max) {
                    max = value;
                    key = next.getKey();
                }
                System.out.println(next);
            }
            System.out.println(key +"："+max);
            list.add(key);
            //移除已覆盖的区域
            address.removeAll(map.get(key));
            for (String s : address) {
                System.out.print(s+",");
            }
        }
        System.out.print("接近完美的组合广播是：");
        for (String s : list) {
            System.out.print(s+",");
        }
    }
}
