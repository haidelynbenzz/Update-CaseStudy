package com.ibm.training.bootcamp.rest.sample01.domain;

import java.math.BigDecimal;

public class OrderItem {

	Long id;
	private BigDecimal Quantity;
	private String OrderItemName;
	private BigDecimal TotalItemPrice;
	
	public OrderItem() {
		
	}
	
	public OrderItem(BigDecimal Quantity, String OrderItemName, BigDecimal TotalItemPrice) {
		this(null, Quantity, OrderItemName, TotalItemPrice);
	}
	
	public OrderItem(Long id, BigDecimal Quantity,String OrderItemName, BigDecimal TotalItemPrice) {
		this.id = id;
		this.Quantity = Quantity;
		this.OrderItemName = OrderItemName;
		this.TotalItemPrice = TotalItemPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuantity() {
		return Quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		Quantity = quantity;
	}
	
	public String getOrderItemName() {
		return OrderItemName;
	}

	public void setOrderItemName(String orderItemName) {
		OrderItemName = orderItemName;
	}

	public BigDecimal getTotalItemPrice() {
		return TotalItemPrice;
	}

	public void setTotalItemPrice(BigDecimal totalItemPrice) {
		TotalItemPrice = totalItemPrice;
	}

	
}
