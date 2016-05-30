package com.kino.database.DAO;

import java.util.List;

public interface SeatDAO 
{
	List<Seat> getSeatListForRoomNumber(int roomNumber);
	List<Seat> getSeatListForSeance(int seanceID);
	List<Seat> getSeatListForIDArray(int[] idArray);
}
