package org.openshift.quickstarts.undertow.servlet;

import java.util.Date;

public class Order {
	
	private String orderID;
	private String product;
	private Integer amount;
	private Double productPrice;
	private Double total;
	private Date date;
	
	
	public Order(String orderid) {
		super();
		this.orderID=orderid;
	}
	
	public Order() {
		super();
	}
	public Order(String orderID, String product, Integer amount, Double productPrice, Double total, Date date) {
		super();
		this.orderID = orderID;
		this.product = product;
		this.amount = amount;
		this.productPrice = productPrice;
		this.total = total;
		this.date = date;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
