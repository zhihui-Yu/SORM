package com.sorm.core;


import com.sorm.bean.ColumnInfo;
import com.sorm.bean.TableInfo;
import com.sorm.utils.JDBCUtils;
import com.sorm.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 负责查询（对外提供服务的核心类）
 * @Author yzh
 * @Date 2020/4/25 13:10
 * @Version 1.0
 */
@SuppressWarnings("all")
public abstract class Query implements Cloneable{

    /**
     *
     * @param back CallBack 的实现类 实现回调
     * @param sql   SQL语句
     * @param params SQL的参数
     * @param clazz 记录要封装到的java类
     * @return 处理结果
     */
    public Object executeQueryTemplate(CallBack back, String sql, Object[] params, Class clazz) {
        Connection conn = DBManager.getConn();
        List list = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            assert conn != null;
            ps = conn.prepareStatement(sql);
            JDBCUtils.handleParams(params, ps);
            System.out.println(ps);
            rs = ps.executeQuery();
            return back.doExecute(conn, ps, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBManager.close(rs,ps,conn);
        }
        return null;
    }
    /**
     * 直接执行一个DML语句
     * @param sql SQL语句
     * @param param 参数
     * @return 执行后影响记录的行数
     */
    public int executeDML(String sql, Object[] param){
        {
            Connection conn = DBManager.getConn();
            PreparedStatement ps = null;

            try {
                assert conn != null;
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
    }

    /**
     * 将一个对象存储到数据库中
     * 把对象中不为null的属性往数据库中存储。为null则放0
     * @param object    要存储的对象
     */
    public int insert(Object object){
        //获取class对象
        Class<?> c = object.getClass();
        List<Object> params = new ArrayList<>();//存储sql参数
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        StringBuilder sql = new StringBuilder("insert into "+tableInfo.getTname()+" (");
        int count = 0;//计算不为空的属性值
        Field[] fs = c.getDeclaredFields();
        for (Field f :fs ) {
            String name = f.getName();
            Object value = ReflectUtils.invokeGet(name, object);
            if (value != null ) {
                count ++ ;
                sql.append(name).append(",");
                params.add(value);
            }
        }
        sql.setCharAt(sql.length()-1,')');
        sql.append(" values (");
        for ( int i = 0; i < count; i++ ) {
            sql.append("?,");
        }
        sql.setCharAt(sql.length()-1,')');
        System.out.println(sql.toString());
        return executeDML(sql.toString(),params.toArray());
    }

    /**
     *  删除clazz 表示类对应的表中的记录(指定主键id)
     * @param clazz 要删除数据的的表名
     * @param id    表主键id
     * @return  影响记录行数
     */
    public int delete(Class clazz, Object id ){
        //通过CLass对象找TableInfo
        TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
        //获得主键
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        String sql = "delete from "+tableInfo.getTname()+" where "+ onlyPriKey.getName()+"=?";
        return executeDML(sql, new Object[]{id});
    }

    /**
     *  删除对象在数据库中对应的记录（对象所在的类对应到表，对象的主键的值对应到记录）
     * @param object    对象
     * @return  影响记录行数
     */
    public int delete(Object object){
        Class<?> c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        //通过反射调用 set、get
        Object priKeyValue = ReflectUtils.invokeGet(onlyPriKey.getName(), object);
        return delete(c,priKeyValue);
    }

    /**
     * 根据主键更新对象对应的记录，并且只更新指定的字段的值
     * @param object 需要更新的记录值
     * @param fieldNames   需要更新的字段名
     * @return 影响记录行数
     */
    public int update(Object object, String[] fieldNames) {
        Class<?> c = object.getClass();
        List<Object> params = new ArrayList<>();//存储sql参数
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo priKey = tableInfo.getOnlyPriKey();//获取主键
        StringBuilder sql = new StringBuilder("update "+tableInfo.getTname()+" set ");

        for (String fname : fieldNames ) {
            Object fValue = ReflectUtils.invokeGet(fname, object);
            params.add(fValue);
            sql.append(fname).append("= ? ,");
        }
        sql.setCharAt(sql.length()-1,' ');
        sql.append("where ");
        sql.append(priKey.getName()).append("=?");
        //添加主键值
        params.add(ReflectUtils.invokeGet(priKey.getName(),object));
        return executeDML(sql.toString(), params.toArray());
    }

    /**
     * 查询返回多行记录，并将记录封装到clazz指定的类对象中
     * @param sql sql语句
     * @param clazz 封装数据的javabean类的Class对象
     * @param params    sql的参数
     * @return  查询的结果
     */
    public List queryRows(String sql, Class clazz, Object[] params) {
        return (List)executeQueryTemplate((conn, ps, rs) -> {
            List list = null;
            try {
                while (rs.next()) {
                    if( list == null ) {
                        list = new ArrayList();
                    }
                    Object rowObj = clazz.newInstance();
                    ResultSetMetaData metaData = rs.getMetaData();
                    //多列
                    for (int i = 0; i < metaData.getColumnCount()-1; i++ ) {
                        String columnName = metaData.getColumnLabel(i+1);
                        Object columnValue = rs.getObject(i+1);
                        ReflectUtils.invokeSet(rowObj, columnName, columnValue);
                    }
                    list.add(rowObj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        },sql,params,clazz);
    }

    /**
     * 查询返回一行记录，并将记录封装到clazz指定的类对象中
     * @param sql sql语句
     * @param clazz 封装数据的javabean类的Class对象
     * @param params sql的参数
     * @return 查询的结果
     */
    public Object queryRow(String sql, Class clazz, Object[] params) {
        List list = queryRows(sql, clazz, params);
        return (list==null && list.size() < 1) ? null:list.get(0);
    }

    /**
     * 查询返回一个值，并将该值返回
     * @param sql sql语句
     * @param params sql的参数
     * @return 查询的结果
     */
    public Object queryVlaue(String sql, Object[] params) {
        return executeQueryTemplate((conn, ps, rs) -> {
            Object value = null;
            try {
                while (rs.next()) {
                    value = rs.getObject(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return value;
        },sql,params,null);
    }

    /**
     * 查询返回一个数字，并将该值返回
     * @param sql sql语句
     * @param params sql的参数
     * @return 查询的结果
     */
    public Number queryNumber(String sql, Object[] params) {
        return (Number) queryVlaue(sql, params);
    }

    /**
     * 分页查询
     * @param pageNum 第几页数据
     * @param size  每页多少条记录
     * @return 查询结果
     */
    public abstract List queryPagenate (int pageNum, int size) ;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
