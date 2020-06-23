package com.cq.gmall.seckill.reflecfandall;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 彭国仁
 * @data 2019/12/29 20:59
 */
public class TestReflect {
    public static void main(String[] args) {
        Class<?> aClass = null;
        try {
           aClass = Class.forName("com.cq.gmall.seckill.reflecfandall.Emp");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
        try {
            Emp e = (Emp)aClass.newInstance();
            Method method = aClass.getDeclaredMethod("setName", String.class);
            method.invoke(e, "张三");
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
