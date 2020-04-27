package com.demo.po;

import java.util.*;
import java.sql.*;


public class Claim_voucher {
	private String next_deal_sn;
	private String create_sn;
	private java.sql.Date create_time;
	private Double total_amount;
	private String cause;
	private Integer id;
	private String status;
	public String getNext_deal_sn() {
		return next_deal_sn;
	}
	public String getCreate_sn() {
		return create_sn;
	}
	public java.sql.Date getCreate_time() {
		return create_time;
	}
	public Double getTotal_amount() {
		return total_amount;
	}
	public String getCause() {
		return cause;
	}
	public Integer getId() {
		return id;
	}
	public String getStatus() {
		return status;
	}
	public void setNext_deal_sn(String next_deal_sn) {
		this.next_deal_sn = next_deal_sn;
	}
	public void setCreate_sn(String create_sn) {
		this.create_sn = create_sn;
	}
	public void setCreate_time(java.sql.Date create_time) {
		this.create_time = create_time;
	}
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}