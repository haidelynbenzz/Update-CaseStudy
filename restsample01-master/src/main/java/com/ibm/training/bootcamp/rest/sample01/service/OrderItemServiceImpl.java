package com.ibm.training.bootcamp.rest.sample01.service;

import com.ibm.training.bootcamp.rest.sample01.dao.FoodJdbcDaoImpl;
import com.ibm.training.bootcamp.rest.sample01.dao.OrderItemDao;
import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class OrderItemServiceImpl implements OrderItemService {

	OrderItemDao orderItemDao;
	
	public OrderItemServiceImpl() {
		this.orderItemDao = FoodJdbcDaoImpl.getInstance();
		//this.orderDao = UserHashMapDaoImpl.getInstance();
	}
	

	@Override
	public List<OrderItem> findAllOrderItem(){
		return orderItemDao.findAllOrderItem();
	}
	
	public OrderItem findOrderItem(Long id) {
		return orderItemDao.findOrderItem(id);
	}
	
	public List<OrderItem> findByOrderItem(String OrderItemName){
		return orderItemDao.findByOrderItem(OrderItemName);
	}
	
	public List<OrderItem> findOrderItemData(){
		return orderItemDao.findOrderItemData();
	}
	
	@Override
	public void addOrderItem(OrderItem orderItem) {
		if (validate(orderItem)) {
			orderItemDao.addOrderItem(orderItem);
		} else {
			throw new IllegalArgumentException("Fields CustomerName, Address and ContactNumber cannot be blank.");
		}
	}
	
	@Override
	public void upsertOrderItem(OrderItem orderItem) {
		if (validate(orderItem)) {
			if(orderItem.getId() != null && orderItem.getId() >= 0) {
				orderItemDao.updateOrderItem(orderItem);
			} else {
				orderItemDao.addOrderItem(orderItem);
			}
		} else {
			throw new IllegalArgumentException("Fields CustomerName, Address and ContactNumber cannot be blank.");
		}
	}
	
	@Override
	public void deleteOrderItem(Long id) {
		orderItemDao.deleteOrderItem(id);
	}
	
	private boolean validate(OrderItem orderItem) {
		return !StringUtils.isAnyBlank(orderItem.getOrderItemName());
	}
}
