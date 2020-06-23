package com.cq.gmall.seckill.controller;

import java.util.Objects;

/**
 * @author 彭国仁
 * @data 2019/11/27 17:25
 */
public class Emplee {
    private String name;
    private int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emplee emplee = (Emplee) o;
        return age == emplee.age &&
                name.equals(emplee.name);
    }
    @Override

    public int hashCode() {
        return Objects.hash(name, age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
