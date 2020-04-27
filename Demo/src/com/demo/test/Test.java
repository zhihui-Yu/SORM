package com.demo.test;

import com.demo.po.Employee;
import com.sorm.core.Query;
import com.sorm.core.QueryFactory;

/**
 * @Author yzh
 * @Date 2020/4/27 22:27
 * @Version 1.0
 */
public class Test {
	public static void main(String[] args) {
		  //生成po类 执行一次就好了
        //TableContext.updateJavaPOFile();
		//insert();
		Test t = new Test();
		t.query();
	}
	public void insert(){
		Employee e = new Employee();
        e.setSn("10016");
        e.setName("wanwu");
        e.setDepartment_sn("10003");
        Query q = QueryFactory.createQuery();
        q.insert(e);
	}
	public void delete(){
		Employee e = new Employee();
        e.setSn("10016");
        e.setName("wanwu");
        e.setDepartment_sn("10003");
        Query q = QueryFactory.createQuery();
        q.delete(e);
	}
	public void query(){
		Employee e = new Employee();
        e.setSn("10016");
        e.setName("wanwu");
        e.setDepartment_sn("10003");
        Query q = QueryFactory.createQuery();
        q.queryRows("select * from employee where sn > ?", Employee.class, new String[]{"1000"} );
        q.queryNumber("select count(*) from employee where sn > ?", new String[]{"1000"} );
        q.queryVlaue("select count(*) from employee where sn > ?", new String[]{"1000"} );
        q.queryById(Employee.class, "10012");
        
	}
	public void update(){
		Employee e = new Employee();
        e.setSn("10016");
        e.setName("hahah");
        e.setDepartment_sn("10003");
        Query q = QueryFactory.createQuery();
        q.update(e, new String[]{"name"});
	}
}
