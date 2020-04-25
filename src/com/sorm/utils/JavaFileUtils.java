package com.sorm.utils;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.JavaFieldGetSet;
import com.sorm.bean.TableInfo;
import com.sorm.core.DBManager;
import com.sorm.core.MySqlTypeConvertor;
import com.sorm.core.TableContext;
import com.sorm.core.TypeConvertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装生成Java文件（源代码）常用操作
 * @Author yzh
 * @Date 2020/4/25 14:02
 * @Version 1.0
 */
public class JavaFileUtils {

    /**
     * 根据字段信息生成Java属性信息
     * @param columnInfo 字段信息
     * @param convertor  类型转换器
     * @return Java属性和set get源码
     */
    public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo columnInfo,
                                                       TypeConvertor convertor){
        String javaFieldType = convertor.databaseType2JavaType(columnInfo.getDataType());
        JavaFieldGetSet jfgs = new JavaFieldGetSet();
        jfgs.setFieldInfo("\tprivate "+javaFieldType+" "+columnInfo.getName()+";\n");

        //get的方法
        StringBuilder sb = new StringBuilder();
        sb.append("\tpublic "+javaFieldType+" get"+StringUtils.firstChar2UpperCase(columnInfo.getName())+"() {\n");
        sb.append("\t\treturn "+columnInfo.getName()+";\n");
        sb.append("\t}\n");
        jfgs.setGetInfo(sb.toString());

        //set
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\tpublic void set"+StringUtils.firstChar2UpperCase(columnInfo.getName())
                +"("+javaFieldType +" "+columnInfo.getName()+") {\n");
        sb2.append("\t\tthis."+columnInfo.getName()+" = "+columnInfo.getName()+";\n");
        sb2.append("\t}\n");
        jfgs.setSetInfo(sb2.toString());


        return jfgs;
    }

    public static String createJavaSrc(TableInfo tableInfo, TypeConvertor convertor) {
        Map<String, ColumnInfo> columns = tableInfo.getColums();
        List<JavaFieldGetSet> javaFields = new ArrayList<>();

        for (ColumnInfo c : columns.values()) {
            javaFields.add(createFieldGetSetSRC(c,convertor));
        }

        StringBuilder src = new StringBuilder();

        //生成package
        src.append("package "+ DBManager.getConfiguration().getPoPackage()+";\n\n");

        //生成import
        src.append("import java.util.*;\n");
        src.append("import java.sql.*;\n\n\n");

        //生成类声明
        src.append("public class "+StringUtils.firstChar2UpperCase(tableInfo.getTname())+" {\n");

        //生成属性
        for (JavaFieldGetSet f : javaFields ) {
            src.append(f.getFieldInfo());
        }

        //生成get
        for (JavaFieldGetSet f : javaFields ) {
            src.append(f.getGetInfo());
        }

        //生成set
        for (JavaFieldGetSet f : javaFields ) {
            src.append(f.getSetInfo());
        }

        //生成结束
        src.append("}");
        return  src.toString();
    }

    public static void createJavaPOFile(TableInfo tableInfo, TypeConvertor convertor) {
        String javaSrc = createJavaSrc(tableInfo, convertor);
        String path = DBManager.getConfiguration().getSrcPath()+"\\"+DBManager.getConfiguration().getPoPackage().replaceAll("\\.","\\\\");

        BufferedWriter bw = null;
        File f = new File(path);
        //目录不存在就创建
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
            bw = new BufferedWriter(new FileWriter(path+"\\"+StringUtils.firstChar2UpperCase(tableInfo.getTname())+".java"));
            bw.write(javaSrc);
            System.out.println("建立表"+tableInfo.getTname()+"对应java类："+StringUtils.firstChar2UpperCase(tableInfo.getTname())+".java");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        ColumnInfo ci = new ColumnInfo("id","int",0);
//        JavaFieldGetSet str = createFieldGetSetSRC(ci, new MySqlTypeConvertor());
//        System.out.println(str.getFieldInfo());
//        System.out.println(str.getGetInfo());
//        System.out.println(str.getSetInfo());

        Map<String, TableInfo> tables = TableContext.tables;
//        String javaSrc = createJavaSrc(tables.get("employee"), new MySqlTypeConvertor());
//        System.out.println(javaSrc);

        for (TableInfo table : tables.values()) {
            createJavaPOFile(tables.get(table.getTname()), new MySqlTypeConvertor());
        }
    }
}
