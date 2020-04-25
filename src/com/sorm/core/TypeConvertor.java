package com.sorm.core;

/**
 * 负责java数据类型和数据库类型互相转换
 * @Author yzh
 * @Date 2020/4/25 13:56
 * @Version 1.0
 */
public interface TypeConvertor {

    /**
     * 将数据库类型转化为Java的数据类型
     * @param columnType    数据库字段的数据类型
     * @return  java的数据类型
     */
    String databaseType2JavaType(String columnType);

    /**
     * 将java类型转为数据库数据类型
     * @param javaDataType java数据类型
     * @return  数据库数据类型
     */
    String javaType2DatabaseType(String javaDataType);
}
