package com.ibm.training.bootcamp.rest.sample01.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.jdbc.JDBCDataSource;

import com.ibm.training.bootcamp.rest.sample01.domain.Food;
import com.ibm.training.bootcamp.rest.sample01.domain.Order;
import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;

public class FoodJdbcDaoImpl implements FoodDao, OrderDao, OrderItemDao {

	
private static FoodJdbcDaoImpl INSTANCE;
	
	private JDBCDataSource dataSource;
	
	static public FoodJdbcDaoImpl getInstance() {
		
		FoodJdbcDaoImpl instance;
		if(INSTANCE != null) {
			instance = INSTANCE;
		}else {
			instance = new FoodJdbcDaoImpl();
			INSTANCE = instance;
		}
		
		return instance;
	}
	
	private FoodJdbcDaoImpl() {
		init();
	}
	
	private void init() {
		dataSource = new JDBCDataSource();
		dataSource.setDatabase("jdbc:hsqldb:mem:USER");
		dataSource.setUser("username");
		dataSource.setPassword("password");
		
		
		createFoodItemTbl();
		insertInitFoodItem();
		
		createOrderTbl();
		insertInitOrder();
		
		createOrderItemTbl();
		insertInitOrderItem();
		
	}
	
	private void createFoodItemTbl() {
		String createSqlFoodItem = "CREATE TABLE FoodItemTbl " 
				+ "(id INTEGER IDENTITY PRIMARY KEY, " 
				+ " FoodItemName VARCHAR(255), "
				+ " UnitPrice DOUBLE," 
				+ " InStock VARCHAR(255))";

		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(createSqlFoodItem);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void insertInitFoodItem() { 

		add(new Food("Pasta",null,"Full Inventory"));
		add(new Food("Pizza Pie",null,"Out Of Stock"));
		add(new Food("Buffalo Wings",null,"Full Inventory"));
		add(new Food("Chicken",null,"Limited Stock"));
		add(new Food("Baby Ribs",null,"Full Inventory"));
	}
	
	
	@Override
	public List<Food> findAll(){
		return findByFoodItemName(null);
	}
	
	@Override
	//FINDING ID
	public Food find(Long id) {

		Food food = null;

		if (id != null) {
			String sqlFood = "SELECT id, FoodItemName, UnitPrice, InStock FROM FoodItemTbl where id = ?";
			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlFood)) {

				ps.setInt(1, id.intValue());
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					food = new Food(Long.valueOf(
							results.getInt("id")), 
							results.getString("FoodItemName"),
							results.getBigDecimal("UnitPrice"), 
							results.getString("InStock"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return food;
	}
	
	@Override
	public List<Food> findByFoodItemName(String FoodItemName) {
		List<Food> foods = new ArrayList<>();

		String sqlFood = "SELECT id, FoodItemName, UnitPrice, InStock FROM FoodItemTbl WHERE FoodItemName LIKE ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlFood)) {

			ps.setString(1, createSearchValue(FoodItemName));

			ResultSet results = ps.executeQuery();

			while (results.next()) {
				Food food = new Food(Long.valueOf(
						results.getInt("id")), 
						results.getString("FoodItemName"),
						results.getBigDecimal("UnitPrice"), 
						results.getString("InStock"));
				foods.add(food);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return foods;
	}
	
	
	private String createSearchValue(String string) {
		
		String value;
		
		if (StringUtils.isBlank(string)) {
			value = "%";
		} else {
			value = string;
		}
		
		return value;
	}
	

	@Override
	public void add(Food food) {
		
		String insertSqlFood = "INSERT INTO FoodItemTbl (FoodItemName, UnitPrice, InStock) VALUES (?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSqlFood)) {

			ps.setString(1, food.getFoodItemName());
			ps.setBigDecimal(2, food.getUnitPrice());
			ps.setString(3, food.getInStock());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Food food) {
		String updateSqlFood = "UPDATE FoodItemTbl SET FoodItemName = ?, UnitPrice = ?, InStock = ? WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSqlFood)) {

			ps.setString(1, food.getFoodItemName());
			ps.setBigDecimal(2, food.getUnitPrice());
			ps.setString(3, food.getInStock());
			ps.setLong(4, food.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@Override
	public void delete(Long id) {
		String updateSqlFood = "DELETE FROM FoodItemTbl WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSqlFood)) {

			ps.setLong(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
//--------------//--------------//--------------//--------------//--------------//--------------//--------------//--------------//
//--------------//--------------//--------------//--------------//--------------//--------------//--------------//--------------//
	
	//ORDERTBL & INITORDER
	
		private void createOrderTbl() {
			String createSqlOrderTbl = "CREATE TABLE OrderTbl " 
					+ "(id INTEGER IDENTITY PRIMARY KEY, " 
					+ " CustomerName VARCHAR(255), "
					+ " Address VARCHAR(255), "
					+ " ContactNumber VARCHAR(255),"
					+ " Status VARCHAR(255),"
					+ " Total DOUBLE)";

			try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

				stmt.executeUpdate(createSqlOrderTbl);

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		private void insertInitOrder() {

			addOrder(new Order("Maria Clara","California","022-011","Received",null));
			addOrder(new Order("Lauren Cimorelli","LA","033-055","Kitchen",null));
			addOrder(new Order("Steve Parker","New York","043-075","In Transit",null));
			addOrder(new Order("Robert Downey Jr.","LA","013-955","Delivered",null));
			addOrder(new Order("Michael Bobley","Canada","053-075","Cancelled",null));
		}
		
		@Override
		public List<Order> findAllOrder(){
			return findByOrder(null,null,null);
		}
		
		@Override
		//FINDING ID
		public Order findOrder(Long id) {

			Order order = null;

			if (id != null) {
				String sqlOrder = "SELECT id, CustomerName, Address, ContactNumber, Status, Total FROM OrderTbl where id = ?";
				try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlOrder)) {

					ps.setInt(1, id.intValue());
					ResultSet results = ps.executeQuery();

					if (results.next()) {
						order = new Order(Long.valueOf(
								results.getInt("id")), 
								results.getString("CustomerName"),
								results.getString("Address"), 
								results.getString("ContactNumber"),
								results.getString("Status"),
								results.getBigDecimal("Total"));
					}

				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}

			return order;
		}
		
		@Override
		public List<Order> findByOrder(String CustomerName, String Address, String ContactNumber) {
			List<Order> orders = new ArrayList<>();

			String sqlOrder = "SELECT id, CustomerName, Address, ContactNumber, Status ,Total FROM OrderTbl WHERE CustomerName LIKE ? AND Address LIKE ? AND ContactNumber LIKE ?";

			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlOrder)) {

				ps.setString(1, createSearchValues(CustomerName));
				ps.setString(2, createSearchValues(Address));
				ps.setString(3, createSearchValues(ContactNumber));
				
				ResultSet results = ps.executeQuery();

				while (results.next()) {
					Order order = new Order(Long.valueOf(
							results.getInt("id")), 
							results.getString("CustomerName"),
							results.getString("Address"), 
							results.getString("ContactNumber"),
							results.getString("Status"),
							results.getBigDecimal("Total"));
					orders.add(order);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

			return orders;
		}
		
		
		private String createSearchValues(String string) {
			
			String value;
			
			if (StringUtils.isBlank(string)) {
				value = "%";
			} else {
				value = string;
			}
			
			return value;
		}
		

		@Override
		public void addOrder(Order order) {
			
			String insertSqlOrder = "INSERT INTO OrderTbl (CustomerName, Address, ContactNumber, Status, Total) VALUES (?, ?, ?, ?, ?)";

			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSqlOrder)) {

				ps.setString(1, order.getCustomerName());
				ps.setString(2, order.getAddress());
				ps.setString(3, order.getContactNumber());
				ps.setString(4, order.getStatus());
				ps.setBigDecimal(5, order.getTotal());
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		@Override
		public void updateOrder(Order order) {
			String updateSqlOrder = "UPDATE OrderTbl SET CustomerName = ?, Address = ?, ContactNumber = ? Status = ?, Total = ? WHERE id = ?";

			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSqlOrder)) {

				ps.setString(1, order.getCustomerName());
				ps.setString(2, order.getAddress());
				ps.setString(3, order.getContactNumber());
				ps.setString(4, order.getStatus());
				ps.setBigDecimal(5, order.getTotal());
				ps.setLong(6, order.getId());
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}


		@Override
		public void deleteOrder(Long id) {
			String updateSqlOrder = "DELETE FROM OrderTbl WHERE id = ?";

			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSqlOrder)) {

				ps.setLong(1, id);
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
//--------------//--------------//--------------//--------------//--------------//--------------//--------------//--------------//
		
		private void createOrderItemTbl() {
			String createSqlOrderItemTbl = "CREATE TABLE OrderItemTbl " 
					+ "(id INTEGER IDENTITY PRIMARY KEY, " 
					+ " Quantity DOUBLE, "
					+ " OrderItemName VARCHAR(255), "
					+ " TotalItemPrice DOUBLE)";

			try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

				stmt.executeUpdate(createSqlOrderItemTbl);

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		

		private void insertInitOrderItem() { 

			addOrderItem(new OrderItem(null,"Order1", null));
			addOrderItem(new OrderItem(null,"Order2", null));
			addOrderItem(new OrderItem(null,"Order3", null));
			addOrderItem(new OrderItem(null,"Order4", null));
			addOrderItem(new OrderItem(null,"Order5", null));
		}
		
		@Override
		public List<OrderItem> findAllOrderItem(){
			return findByOrderItem(null);
		}
		
		@Override
		//FINDING ID
		public OrderItem findOrderItem(Long id) {

			OrderItem orderItem = null;

			if (id != null) {
				String sqlFood = "SELECT id, Quantity, OrderItemName, TotalItemPrice FROM OrderItemTbl where id = ?";
				try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlFood)) {

					ps.setInt(1, id.intValue());
					ResultSet results = ps.executeQuery();

					if (results.next()) {
						orderItem = new OrderItem(Long.valueOf(
								results.getInt("id")), 
								results.getBigDecimal("Quantity"),
								results.getString("OrderItemName"),
								results.getBigDecimal("TotalItemPrice")); 
					}

				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}

			return orderItem;
		}

		
		@Override
		public List<OrderItem> findByOrderItem(String OrderItemName) {
			List<OrderItem> orderItems = new ArrayList<>();

			String sqlFood = "SELECT id, Quantity, OrderItemName, TotalItemPrice FROM OrderItemTbl WHERE OrderItemName LIKE ?";

			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlFood)) {

				ps.setString(1, createSearchValueOrderItem(OrderItemName));

				ResultSet results = ps.executeQuery();

				while (results.next()) {
					OrderItem orderItem = new OrderItem(Long.valueOf(
							results.getInt("id")), 
							results.getBigDecimal("Quantity"),
							results.getString("OrderItemName"),
							results.getBigDecimal("TotalItemPrice"));
					orderItems.add(orderItem);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

			return orderItems;
		}
		
		
		private String createSearchValueOrderItem(String string) {
			
			String value;
			
			if (StringUtils.isBlank(string)) {
				value = "%";
			} else {
				value = string;
			}
			
			return value;
		}
		
		@Override
		public void addOrderItem(OrderItem orderItem) {
			
			String insertSqlFood = "INSERT INTO OrderItemTbl (Quantity, OrderItemName, TotalItemPrice) VALUES (?, ?, ?)";

			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSqlFood)) {

				ps.setBigDecimal(1, orderItem.getQuantity());
				ps.setString(2, orderItem.getOrderItemName());
				ps.setBigDecimal(3, orderItem.getTotalItemPrice());
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		@Override
		public void updateOrderItem(OrderItem orderItem) {
			String updateSqlFood = "UPDATE OrderItemTbl SET Quantity = ?, OrderItemName = ?, TotalItemPrice = ? WHERE id = ?";

			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSqlFood)) {

				ps.setBigDecimal(1, orderItem.getQuantity());
				ps.setString(2, orderItem.getOrderItemName());
				ps.setBigDecimal(3, orderItem.getTotalItemPrice());
				ps.setLong(4, orderItem.getId());
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}


		@Override
		public void deleteOrderItem(Long id) {
			String updateSqlFood = "DELETE FROM OrderItemTbl WHERE id = ?";

			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSqlFood)) {

				ps.setLong(1, id);
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		@Override
		public List<OrderItem> findOrderItemData() {
			List<OrderItem> orderItems = new ArrayList<>();

			String inner = 
					"SELECT f.id, f.unitPrice , o.CustomerName, oItem.id, oItem.Quantity, oItem.orderItemName, oItem.TotalItemPrice  "
					+ "FROM FoodItemTbl f "
					+ "INNER JOIN OrderTbl o "
					+ "ON f.id = o.id "
					+ "INNER JOIN OrderItemTbl oItem "
					+ "ON o.id = oItem.id";
			
			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(inner)) {

				
				ResultSet results = ps.executeQuery();

				while (results.next()) {
					
							int fid = results.getInt("id");
							int oid = results.getInt("id");
							String uprice = results.getString("unitPrice");
							String custName = results.getString("CustomerName");
							String oItemName = results.getString("orderItemName");
							String qty = results.getString("Quantity");
							String total = results.getString("TotalItemPrice");
							System.out.println(fid+" "+oid+" "+uprice+" "+custName+" "+oItemName+" "+qty+" "+total);
				
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

			System.out.println();
			return orderItems;
		}
}
