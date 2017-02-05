package org.primefaces.showcase.domain;

import java.io.Serializable;

public class Car implements Serializable { 
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8297759005751642815L;
	private String id;
	 private String brand;
	 private Integer year;
	 private String color;
	 private Integer price;
	 private Boolean soldState;

//	public Car(String randomId, String randomBrand, int randomYear, String randomColor, int randomPrice,
//			boolean randomSoldState) {
//		// TODO Auto-generated constructor stub
//	}
	 
	public Car(String id, String brand, Integer year, String color, Integer price, Boolean soldState) {
		super();
		this.id = id;
		this.brand = brand;
		this.year = year;
		this.color = color;
		this.price = price;
		this.soldState = soldState;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Boolean getSoldState() {
		return soldState;
	}

	public void setSoldState(Boolean soldState) {
		this.soldState = soldState;
	}


}
