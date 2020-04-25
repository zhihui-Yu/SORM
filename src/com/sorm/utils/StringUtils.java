package com.sorm.utils;

/**
 * 封装了字符串常用的操作
 * @Author yzh
 * @Date 2020/4/25 14:03
 * @Version 1.0
 */
public class StringUtils {
    public static String firstChar2UpperCase(String string){
        return string.toUpperCase().substring(0,1)+string.substring(1);
    }
}
