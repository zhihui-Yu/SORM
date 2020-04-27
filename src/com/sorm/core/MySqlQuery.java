package com.sorm.core;

import java.util.List;

/**
 * @Author yzh
 * @Date 2020/4/25 19:24
 * @Version 1.0
 */
public class MySqlQuery extends Query {
/*
    public static void main(String[] args) {
        Employee e = new Employee();
        e.setSn("10011");
        e.setDepartment_sn("10005");
        e.setName("hhahaha");
        //new MySqlQuery().delete(e);
        //new MySqlQuery().insert(e);
        //new MySqlQuery().update(e,new String[]{"name"});

        List list = new MySqlQuery().queryRows("select sn, name, post from employee where sn>?",
                Employee.class, new Object[]{"10000"});
        System.out.println(list);*//*

        Object o = new MySqlQuery().queryVlaue("select count(*) from employee ", new Object[]{});
        //Number o = new MySqlQuery().queryNumber("select count(*) from employee ", new Object[]{});
        System.out.println(o);
    }
*/

    @Override
    public List queryPagenate(int pageNum, int size) {
        return null;
    }
}
