package com.sorm.test;

import com.sorm.core.QueryFactory;

/**
 * 连接池效率测试
 * 不使用连接池 1000次 3578ms 10000次 22819ms
 * 使用连接池   1000次 860ms  10000次 2879ms
 * @Author yzh
 * @Date 2020/4/27 20:56
 * @Version 1.0
 */
public class PoolTest {
    private static int size = 10000;
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++ ) {
            test();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
    public static void test(){
        Object o = QueryFactory.createQuery().queryVlaue("select count(*) from employee ", new Object[]{});
        System.out.println(o);
    }
}
