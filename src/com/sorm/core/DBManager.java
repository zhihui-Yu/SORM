package com.sorm.core;

import com.sorm.bean.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * 根据配置信息，维护连接对象的管理（新增连接池功能）
 * @Author yzh
 * @Date 2020/4/25 14:01
 * @Version 1.0
 */
public class DBManager {
    private static Configuration conf;

    static {//静态代码块 -- 只加载一次
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        conf = new Configuration();
        conf.setDriver(properties.getProperty("driver"));
        conf.setPoPackage(properties.getProperty("poPackage"));
        conf.setPwd(properties.getProperty("pwd"));
        conf.setSrcPath(properties.getProperty("srcPath"));
        conf.setUrl(properties.getProperty("url"));
        conf.setUser(properties.getProperty("user"));
        conf.setUsingDB(properties.getProperty("usingDB"));
    }

    public static Connection getConn () {
        try {
            Class.forName(conf.getDriver());
            //直接建立连接，后期增加连接池 提高效率
            return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPwd());
        }catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }

    public static void close (ResultSet rs, Statement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if( ps != null ) {
                ps.close();
            }
            if (conn != null ) {
                conn.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Configuration getConfiguration(){ return conf;}
}

