package com.ibm.training.bootcamp.rest.sample01.restcontroller;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.domain.Order;
import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;
import com.ibm.training.bootcamp.rest.sample01.service.OrderItemService;
import com.ibm.training.bootcamp.rest.sample01.service.OrderItemServiceImpl;

@Path("/OrderItem")
public class OrderItemController {


	private OrderItemService orderItemService;

	public OrderItemController() {
		this.orderItemService = new OrderItemServiceImpl();
	}

	@GET
	@Path("/orderItems")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderItem> getOrderItem(
			@QueryParam("Quantity") BigDecimal Quantity, 
			@QueryParam("OrderItemName") String OrderItemName,
			@QueryParam("TotalItemPrice") BigDecimal TotalItemPrice) {

		try {
			List<OrderItem> orderItems = null;
			
			if (StringUtils.isBlank(OrderItemName)) {
				orderItems = orderItemService.findOrderItemData();
				
				System.out.println("sad");
			} else {
				orderItems = orderItemService.findByOrderItem(OrderItemName);
			}
						
			return orderItems;
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderItem> getOrderItems(
			@QueryParam("Quantity") BigDecimal Quantity, 
			@QueryParam("OrderItemName") String OrderItemName,
			@QueryParam("TotalItemPrice") BigDecimal TotalItemPrice) {

		try {
			List<OrderItem> orderItems;
			
			if (StringUtils.isAllBlank(OrderItemName)) {
				orderItems = orderItemService.findAllOrderItem();
			} else {
				orderItems = orderItemService.findByOrderItem(OrderItemName);
			}
						
			return orderItems;
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrderItem getOrderItem(@PathParam("id") String id) {

		try {
			Long longId = Long.parseLong(id);
			OrderItem orderItem = orderItemService.findOrderItem(longId);
			return orderItem;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOrderItem(OrderItem orderItem) {

		try {
			orderItemService.addOrderItem(orderItem);
			String result = "Order Item List saved : " + orderItem.getQuantity() + " " + orderItem.getOrderItemName() + "  " + orderItem.getTotalItemPrice();
			return Response.status(201).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderItem(@PathParam("id") Long id,OrderItem orderItem) {

		try {
			orderItem.setId(id);
			orderItemService.upsertOrderItem(orderItem);
			String result = "Order Item List updated : " + id + " " + orderItem.getQuantity() + " " + orderItem.getOrderItemName() + "  " + orderItem.getTotalItemPrice();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@DELETE
	@Path("{id}")
	public Response deleteOrderItem(@PathParam("id") String id) {

		try {
			Long longId = Long.parseLong(id);
			orderItemService.deleteOrderItem(longId);
			String result = "Order Item List deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
}
