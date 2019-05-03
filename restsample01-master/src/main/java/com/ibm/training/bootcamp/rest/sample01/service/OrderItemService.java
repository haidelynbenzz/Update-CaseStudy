package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Order;
import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;

public interface OrderItemService {

	public List<OrderItem> findAllOrderItem();
	
	public OrderItem findOrderItem(Long id);
	
	public List<OrderItem> findByOrderItem(String OrderItemName);
	
	public List<OrderItem> findOrderItemData();
	
	public void addOrderItem(OrderItem orderItem);
	
	public void upsertOrderItem(OrderItem orderItem);
	
	public void deleteOrderItem(Long id);

	

}
