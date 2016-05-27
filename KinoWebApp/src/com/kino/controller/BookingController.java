package com.kino.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kino.database.DAO.Booking;
import com.kino.database.DAO.Movie;
import com.kino.database.DAO.Seat;
import com.kino.database.connector.SqliteDAO;

@Controller
public class BookingController {
	@Autowired
	
	private SqliteDAO sqliteDAO;
	
	@RequestMapping(value="/booking_select", method = RequestMethod.POST)
	public String displayPOST(@RequestParam(value="seat",required=false) String seats[],Map<String, Object> model) {
		if(seats!=null){
			for(int i=0;i<seats.length;i++){
				System.out.println("Miejsce "+i+": "+seats[i]);
			}
			model.put("message", "Moje miejsca:");
			model.put("seats", seats);
		}
		
		return "booking_select";
	}
	
	@RequestMapping(value="/booking_select", method = RequestMethod.GET)
	public String displayGET(Map<String, Object> model) {
		List<Seat> seatList=sqliteDAO.getSeatListForRoomNumber(1);
		for(int i=0;i<seatList.size();i++){
			System.out.println("Rz¹d "+seatList.get(i).getRowNumber()+" ,miejsce "+seatList.get(i).getSeatNumber());
		}
		List<Booking> bookingList=sqliteDAO.getBookingForSeanceID(5);
		for(int i=0;i<bookingList.size();i++){
			System.out.println("Rezerwacja "+bookingList.get(i).getID());
			System.out.println("-u¿ytkownik: "+bookingList.get(i).getLogin());
			System.out.println("-ID rezerwacji: "+bookingList.get(i).getID());
			System.out.println("-ID seansu: "+bookingList.get(i).getSeanceID());
			System.out.println("-ID siedzenia: "+bookingList.get(i).getSeatID());
			System.out.println("-kod rezerwacji: "+bookingList.get(i).getCode());
			System.out.println("-cena ulgowa?: "+bookingList.get(i).getLowerPrice());
		}
		model.put("message", "Wybierz miejsca:");
		return "booking_select";
	}
	
}
