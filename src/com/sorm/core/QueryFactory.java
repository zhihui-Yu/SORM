package com.sorm.core;

/**
 * 创建Query对象的工厂类
 * @Author yzh
 * @Date 2020/4/25 13:56
 * @Version 1.0
 */
public class QueryFactory {

    private static QueryFactory factory = new QueryFactory();
    private static Query query = null;
    private QueryFactory() {}

    static {
        try {
            Class c = Class.forName(DBManager.getConfiguration().getQueryClass());
            query = (Query)c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Query createQuery() {
        try {
            return (Query)query.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
