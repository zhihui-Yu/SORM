package com.sorm.bean;

/**
 * 管理配置信息
 * @Author yzh
 * @Date 2020/4/25 14:08
 * @Version 1.0
 */
public class Configuration {
    /**
     * 驱动类
     */
    private String  driver;

    /**
     * jdbc url
     */
    private String  url;

    /**
     * 数据库用户名
     */
    private String  user;

    /**
     * 数据库密码
     */
    private String  pwd;

    /**
     * 正在使用哪种数据库
     */
    private String  usingDB;

    /**
     * 项目源码路径
     */
    private String  srcPath;

    /**
     * 扫描生成Java类的包地址
     */
    private String  poPackage;

    public Configuration(String driver, String url, String user, String pwd, String usingDB, String srcPath, String poPackage) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.pwd = pwd;
        this.usingDB = usingDB;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsingDB() {
        return usingDB;
    }

    public void setUsingDB(String usingDB) {
        this.usingDB = usingDB;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }

    public Configuration() {
    }
}
