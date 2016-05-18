package com.kino.database.DAO;

public interface PriceListDAO 
{
	double getNormalPriceForDay(int day);
	double setNormalPriceForDay(int day,double price);
	double getLowerPriceForDay(int day);
	double setLowerPriceForDay(int day,double price);	
}
