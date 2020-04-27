package com.demo.po;

import java.util.*;
import java.sql.*;


public class Employee {
	private String password;
	private String post;
	private String department_sn;
	private String name;
	private String sn;
	public String getPassword() {
		return password;
	}
	public String getPost() {
		return post;
	}
	public String getDepartment_sn() {
		return department_sn;
	}
	public String getName() {
		return name;
	}
	public String getSn() {
		return sn;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public void setDepartment_sn(String department_sn) {
		this.department_sn = department_sn;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
}