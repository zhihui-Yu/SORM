package com.demo.po;

import java.util.*;
import java.sql.*;


public class Department {
	private String address;
	private String name;
	private String sn;
	public String getAddress() {
		return address;
	}
	public String getName() {
		return name;
	}
	public String getSn() {
		return sn;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
}