package com.sorm.core;

import java.util.List;

/**
 * 负责查询（对外提供服务的核心类）
 * @Author yzh
 * @Date 2020/4/25 13:10
 * @Version 1.0
 */
public interface Query {
    /**
     * 直接执行一个DML语句
     * @param sql SQL语句
     * @param param 参数
     * @return 执行后影响记录的行数
     */
    int executeDML(String sql, Object[] param);

    /**
     * 将一个对象存储到数据库中
     * 把对象中不为null的属性往数据库中存储。为null则放0
     * @param object    要存储的对象
     */
    void insert(Object object);

    /**
     *  删除clazz 表示类对应的表中的记录(指定主键id)
     * @param clazz 要删除数据的的表名
     * @param id    表主键id
     * @return  影响记录行数
     */
    int delete(Class clazz, Object id );

    /**
     *  删除对象在数据库中对应的记录（对象所在的类对应到表，对象的主键的值对应到记录）
     * @param object    对象
     * @return  影响记录行数
     */
    int delete(Object object);

    /**
     * 更新对象对应的记录，并且只更新指定的字段的值
     * @param object 需要更新的记录值
     * @param fieldNames   需要更新的字段名
     * @return 影响记录行数
     */
    int update(Object object, String[] fieldNames);

    /**
     * 查询返回多行记录，并将记录封装到clazz指定的类对象中
     * @param sql sql语句
     * @param clazz 封装数据的javabean类的Class对象
     * @param params    sql的参数
     * @return  查询的结果
     */
    List queryRows(String sql, Class clazz, Object[] params);

    /**
     * 查询返回一行记录，并将记录封装到clazz指定的类对象中
     * @param sql sql语句
     * @param clazz 封装数据的javabean类的Class对象
     * @param params sql的参数
     * @return 查询的结果
     */
    Object queryRow(String sql, Class clazz, Object[] params);

    /**
     * 查询返回一个值，并将该值返回
     * @param sql sql语句
     * @param params sql的参数
     * @return 查询的结果
     */
    Object queryVlaue(String sql, Object[] params);

    /**
     * 查询返回一个数字，并将该值返回
     * @param sql sql语句
     * @param params sql的参数
     * @return 查询的结果
     */
    Number queryNumber(String sql, Object[] params);
}
