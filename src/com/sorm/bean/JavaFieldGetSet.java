package com.sorm.bean;

/**
 * 封装了Java属性和get、set方法的源码
 * @Author yzh
 * @Date 2020/4/25 15:16
 * @Version 1.0
 */
public class JavaFieldGetSet {

    /**
     * 属性的源码信息 如：private int userid
     */
    private String fieldInfo;

    /**
     * get方法的源代码信息 如：public int getUserId(){}
     */
    private String getInfo;

    /**
     * set方法源码信息 如：public void setUserId(){}
     */
    private String setInfo;

    public String getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public String getGetInfo() {
        return getInfo;
    }

    public void setGetInfo(String getInfo) {
        this.getInfo = getInfo;
    }

    public String getSetInfo() {
        return setInfo;
    }

    public void setSetInfo(String setInfo) {
        this.setInfo = setInfo;
    }

    public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
        this.fieldInfo = fieldInfo;
        this.getInfo = getInfo;
        this.setInfo = setInfo;
    }

    public JavaFieldGetSet() {
    }
}
