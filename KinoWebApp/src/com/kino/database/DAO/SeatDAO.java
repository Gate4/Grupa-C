package com.kino.database.DAO;

import java.util.List;

public interface SeatDAO 
{
	List<Seat> getSeatListForRoomNumber(int roomNumber);
}
