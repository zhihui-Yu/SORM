package com.demo.po;

import java.util.*;
import java.sql.*;


public class Claim_voucher_item {
	private String item;
	private Double amount;
	private Integer claim_voucher_id;
	private String comment;
	private Integer id;
	public String getItem() {
		return item;
	}
	public Double getAmount() {
		return amount;
	}
	public Integer getClaim_voucher_id() {
		return claim_voucher_id;
	}
	public String getComment() {
		return comment;
	}
	public Integer getId() {
		return id;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setClaim_voucher_id(Integer claim_voucher_id) {
		this.claim_voucher_id = claim_voucher_id;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}