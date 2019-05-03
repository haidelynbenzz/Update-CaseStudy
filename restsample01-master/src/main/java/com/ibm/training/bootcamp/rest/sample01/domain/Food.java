package com.ibm.training.bootcamp.rest.sample01.domain;

import java.math.BigDecimal;

public class Food {

	//FOODITEMTBL FIELDS
		Long id;
		private String FoodItemName;
		private BigDecimal UnitPrice;
		private String InStock;


		
		public Food() {
			
		}
		
		
		//FOODITEMTBL METHOD
		public Food(String FoodItemName, BigDecimal UnitPrice, String InStock) {
			this(null, FoodItemName, UnitPrice, InStock);
		}
		
		public Food(Long id, String FoodItemName, BigDecimal UnitPrice, String InStock) {
			this.id = id;
			this.FoodItemName = FoodItemName;
			this.UnitPrice = UnitPrice;
			this.InStock = InStock;
		}

		
		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getFoodItemName() {
			return FoodItemName;
		}


		public void setFoodItemName(String foodItemName) {
			FoodItemName = foodItemName;
		}


		public BigDecimal getUnitPrice() {
			return UnitPrice;
		}


		public void setUnitPrice(BigDecimal unitPrice) {
			UnitPrice = unitPrice;
		}


		public String getInStock() {
			return InStock;
		}


		public void setInStock(String inStock) {
			InStock = inStock;
		}



}
