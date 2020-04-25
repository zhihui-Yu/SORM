package com.sorm.core;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.TableInfo;
import com.sorm.utils.JavaFileUtils;
import com.sorm.utils.StringUtils;
import sun.security.timestamp.TSRequest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构。
 * @Author yzh
 * @Date 2020/4/25 14:00
 * @Version 1.0
 */
public class TableContext {
    /**
     * 表名为key 表信息为vlaue
     */
    public static Map<String, TableInfo> tables = new HashMap<>();

    /**
     * 将po的class 对象和表信息对象关联起来 便于重用。
     */
    public static Map<Class,TableInfo> poClassTableMap = new HashMap<>();

    private TableContext () {}

    static {
        try {
            //初始化获得表的信息
            Connection conn = DBManager.getConn();
            DatabaseMetaData dbmd = conn.getMetaData();

            ResultSet tableRet = dbmd.getTables(null,"%",
                    "%", new String[]{"TABLE"});

            while ( tableRet.next() ) {
                String tableName = (String) tableRet.getObject("TABLE_NAME");

                TableInfo ti = new TableInfo(tableName,new ArrayList<ColumnInfo>(),
                        new HashMap<String,ColumnInfo>());
                tables.put(tableName, ti);

                ResultSet rs = dbmd.getColumns(null, "%",tableName,"%");

                while ( rs.next() ) {
                    ColumnInfo ci = new ColumnInfo(rs.getString("COLUMN_NAME"),
                            rs.getString("TYPE_NAME"),0 );
                    ti.getColums().put(rs.getString("COLUMN_NAME"),ci);
                }

                ResultSet rs2 = dbmd.getPrimaryKeys(null,"%",tableName);
                while (rs2.next() ) {
                    ColumnInfo ci2 = (ColumnInfo) ti.getColums().get(rs2.getObject("COLUMN_NAME"));
                    ci2.setKeyType(1);//设为主键
                    ti.getPriKeys().add(ci2);
                }
                if (ti.getPriKeys().size()>0 ) {
                    ti.setOnlyPriKey(ti.getPriKeys().get(0));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        //更新类结构
        updateJavaPOFile();

        //加载PO包下类放入poClassTableMap中
        loadPOTables();
    }

    public static void loadPOTables() {
        for (TableInfo tableInfo : tables.values() ) {
            try {
                Class c = Class.forName(DBManager.getConfiguration().getPoPackage()+"."
                            + StringUtils.firstChar2UpperCase(tableInfo.getTname()));
                poClassTableMap.put(c, tableInfo);
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据表结构，更新配置--po包下的java类
     */
    public static void updateJavaPOFile() {
        Map<String, TableInfo> tables = TableContext.tables;

        for (TableInfo table : tables.values()) {
            JavaFileUtils.createJavaPOFile(tables.get(table.getTname()), new MySqlTypeConvertor());
        }
    }

    public static Map<String, TableInfo> getTableInfo() {
        return tables;
    }

    public static void main(String[] args) {
        Map<String,TableInfo> tables = getTableInfo();
        System.out.println(tables);
    }
}
