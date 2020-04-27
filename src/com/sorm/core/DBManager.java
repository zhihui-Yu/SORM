package com.sorm.core;

import com.sorm.bean.Configuration;
import com.sorm.pool.DBConnPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 根据配置信息，维护连接对象的管理（新增连接池功能）
 * @Author yzh
 * @Date 2020/4/25 14:01
 * @Version 1.0
 */
public class DBManager {
    /**
     * 配置信息
     */
    private static Configuration conf;

    /**
     * 连接池对象
     */
    private static DBConnPool pool = null;

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
        conf.setQueryClass(properties.getProperty("queryClass"));
        conf.setPoolMinSize(Integer.parseInt(properties.getProperty("poolMinSize") == null
                || properties.getProperty("poolMinSize").equals("")
                ?"0":properties.getProperty("poolMinSize")));
        conf.setPoolMaxSize(Integer.parseInt(properties.getProperty("poolMaxSize") == null
                || properties.getProperty("poolMaxSize").equals("")
                ?"0":properties.getProperty("poolMaxSize")));
        //初始化javabean
        System.out.println(TableContext.class);
    }

    /**
     * 获取conn对象
     * @return connection对象
     */
    public static Connection getConn () {
        if ( pool == null ) {
            pool = new DBConnPool();
        }
        return pool.getConnection();
    }

    /**
     * 创建conn对象
     * @return connection对象
     */
    public static Connection createConn () {
        try {
            Class.forName(conf.getDriver());
            //直接建立连接，后期增加连接池 提高效率
            return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPwd());
        }catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }

    /**
     * 关闭传入的流
     * @param rs 结果流
     * @param ps 预处理流
     * @param conn 连接
     */
    public static void close (ResultSet rs, Statement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if( ps != null ) {
                ps.close();
            }
//            if (conn != null ) {
//                conn.close();
//            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        pool.close(conn);
    }
    public static Configuration getConfiguration(){ return conf;}
}

