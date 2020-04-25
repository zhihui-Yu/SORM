package com.sorm.bean;

import java.util.List;
import java.util.Map;

/**
 * 存储表结构信息
 * @Author yzh
 * @Date 2020/4/25 14:09
 * @Version 1.0
 */
public class TableInfo {

    /**
     * 表名
     */
    private String tname;

    /**
     * 所有字段的信息
     */
    private Map<String,ColumnInfo> colums;

    /**
     * 唯一主键(目前只能处理表中有且只有一个主键情况)
     */
    private ColumnInfo onlyPriKey;

    /**
     * 联合主键
     */
    private List<ColumnInfo> priKeys;

    public List<ColumnInfo> getPriKeys() {
        return priKeys;
    }

    public void setPriKeys(List<ColumnInfo> priKeys) {
        this.priKeys = priKeys;
    }

    public TableInfo() {
    }

    public TableInfo(String tname, List<ColumnInfo> priKeys,Map<String, ColumnInfo> colums ) {
        this.tname = tname;
        this.colums = colums;
        this.priKeys = priKeys;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Map<String, ColumnInfo> getColums() {
        return colums;
    }

    public void setColums(Map<String, ColumnInfo> colums) {
        this.colums = colums;
    }

    public ColumnInfo getOnlyPriKey() {
        return onlyPriKey;
    }

    public void setOnlyPriKey(ColumnInfo onlyPriKey) {
        this.onlyPriKey = onlyPriKey;
    }
}
