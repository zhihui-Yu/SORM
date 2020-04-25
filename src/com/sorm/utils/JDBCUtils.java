package com.sorm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 封装JDBC常用查询
 * @Author yzh
 * @Date 2020/4/25 14:02
 * @Version 1.0
 */
public class JDBCUtils {

    /**
     * 给SQL传参
     * @param params 参数
     * @param ps 预编译sql
     */
    public static void handleParams(Object[] params, PreparedStatement ps) {
        int count = 0;
        if (params != null) {
            for (Object obj : params ) {
                try {
                    ps.setObject(1+(count++), obj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
