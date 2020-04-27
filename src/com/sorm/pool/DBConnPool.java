package com.sorm.pool;

import com.sorm.core.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 连接池的类
 * @Author yzh
 * @Date 2020/4/27 20:34
 * @Version 1.0
 */
public class DBConnPool {
    /**
     * 连接对象
     */
    private List<Connection> pool;

    /**
     * 最大连接数
     */
    private static final int POOL_MAX_SIZE = DBManager.getConfiguration().getPoolMaxSize() == 0 ? 100 : DBManager.getConfiguration().getPoolMaxSize();

    /**
     * 最小连接数
     */
    private static final int POOL_MIN_SIZE = DBManager.getConfiguration().getPoolMinSize() == 0 ? 100 : DBManager.getConfiguration().getPoolMinSize();

    /**
     * 初始化连接池 使其达到最小连接数
     */
    public void initPool() {
        if( pool == null ) {
            pool = new ArrayList<>();
        }
        while ( pool.size() < POOL_MIN_SIZE ) {
            pool.add(DBManager.createConn());
        }
        System.out.println("初始化连接池，池中conn对象有"+pool.size()+"个");
    }

    public DBConnPool() {
        initPool();
    }

    /**
     *  返回Connection对象
     * @return Connection 对象
     */
    public synchronized Connection getConnection(){
        int last_index = pool.size()-1;
        Connection conn = null;
        if( last_index >= 0 ) {
            conn = pool.get(last_index);
            pool.remove(last_index);
        } else {
            conn = DBManager.createConn();
        }
        return conn;
    }

    /**
     * 将连接放入池中
     * @param conn 连接对象
     */
    public synchronized void close(Connection conn) {
        if( pool.size() >= POOL_MAX_SIZE ) {
            try {
                if(conn != null ) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            pool.add(conn);
        }
    }
}
