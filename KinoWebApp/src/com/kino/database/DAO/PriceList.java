package com.kino.database.DAO;

public class PriceList {
	private int day;
	private double normalPrice;
	private double lowerPrice;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	public double getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(double normalPrice) {
		this.normalPrice = normalPrice;
	}
	
	public double getLowerPrice() {
		return lowerPrice;
	}

	public void setLowerPrice(double lowerPrice) {
		this.lowerPrice = lowerPrice;
	}
	
}
