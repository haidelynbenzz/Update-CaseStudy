package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;

public interface OrderItemDao {

	public OrderItem findOrderItem(Long id);
	
	public List<OrderItem> findAllOrderItem(); 
	
	public List<OrderItem> findByOrderItem(String OrderItemName);

	public List<OrderItem> findOrderItemData();
	
	public void addOrderItem(OrderItem orderItem);
	
	public void updateOrderItem(OrderItem orderItem);
	
	public void deleteOrderItem(Long id);

	
	//public void selectOrderItem(OrderItem orderItem);
}
