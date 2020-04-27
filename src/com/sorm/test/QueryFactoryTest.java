package com.sorm.test;

import com.sorm.core.QueryFactory;

/**
 * 测试工厂类
 * @Author yzh
 * @Date 2020/4/27 20:25
 * @Version 1.0
 */
public class QueryFactoryTest {
    public static void main(String[] args) {
        Object o = QueryFactory.createQuery().queryVlaue("select count(*) from employee ", new Object[]{});
        System.out.println(o);
    }
}
