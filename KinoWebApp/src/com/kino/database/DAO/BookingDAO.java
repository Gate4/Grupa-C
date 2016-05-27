package com.kino.database.DAO;

import java.util.List;

public interface BookingDAO 
{
	List<Booking> getBookingForSeanceID(int seanceID);
}
