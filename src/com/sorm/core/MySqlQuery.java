package com.sorm.core;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.TableInfo;
import com.sorm.po.Employee;
import com.sorm.utils.JDBCUtils;
import com.sorm.utils.ReflectUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author yzh
 * @Date 2020/4/25 19:24
 * @Version 1.0
 */
public class MySqlQuery implements Query {
    public static void main(String[] args) {
        Employee e = new Employee();
        e.setSn("10011");
        new MySqlQuery().delete(e);
    }
    @Override
    public int executeDML(String sql, Object[] param) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            //给sql 参数
            JDBCUtils.handleParams(param,ps);
            System.out.println(ps);
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.close(null, ps, conn);
        }
        return 0;
    }

    @Override
    public void insert(Object object) {

    }

    @Override
    public int delete(Class clazz, Object id) {
        //通过CLass对象找TableInfo
        TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
        //获得主键
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        String sql = "delete from "+tableInfo.getTname()+" where "+ onlyPriKey.getName()+"=?";
        return executeDML(sql, new Object[]{id});
    }

    @Override
    public int delete(Object object) {
        Class<?> c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        //通过反射调用 set、get
        Object priKeyValue = ReflectUtils.invokeGet(onlyPriKey.getName(), object);
        return delete(c,priKeyValue);
    }

    @Override
    public int update(Object object, String[] fieldNames) {
        return 0;
    }

    @Override
    public List queryRows(String sql, Class clazz, Object[] params) {
        return null;
    }

    @Override
    public Object queryRow(String sql, Class clazz, Object[] params) {
        return null;
    }

    @Override
    public Object queryVlaue(String sql, Object[] params) {
        return null;
    }

    @Override
    public Number queryNumber(String sql, Object[] params) {
        return null;
    }
}
