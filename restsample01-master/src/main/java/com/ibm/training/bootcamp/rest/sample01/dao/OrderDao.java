package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Order;
import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;

public interface OrderDao {

	public Order findOrder(Long id);
	
	public List<Order> findAllOrder(); 
	
	public List<Order> findByOrder(String CustomerName, String Address, String ContactNumber);
	
	//public List<Order> findByOrderItem(String CustomerName, String Address, String ContactNumber);

	//public List<FoodDelivery> findByDelivery(String FoodItemName, DecimalFormat UnitPrice, boolean InStock);
	
	public void addOrder(Order order);
	
	public void updateOrder(Order order);
	
	public void deleteOrder(Long id);
	
	//public void selectOrder(Order order);
}
