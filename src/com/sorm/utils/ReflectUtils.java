package com.sorm.utils;

import java.lang.reflect.Method;

/**
 * 封装了反射常用的操作
 * @Author yzh
 * @Date 2020/4/25 14:03
 * @Version 1.0
 */
public class ReflectUtils {
    /**
     * 调用object 对象对应属性的FieldName的get方法
     * @param fieldName 属性名称
     * @param object    对象名称
     * @return 返回get结果
     */
    public static Object invokeGet(String fieldName, Object object) {
        try {
            Class c = object.getClass();
            Method method = c.getMethod("get" +
                    StringUtils.firstChar2UpperCase(fieldName), null);
            return method.invoke(object, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
