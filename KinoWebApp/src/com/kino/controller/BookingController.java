package com.kino.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kino.database.DAO.Booking;
import com.kino.database.DAO.Movie;
import com.kino.database.DAO.Seance;
import com.kino.database.DAO.Seat;
import com.kino.database.DAO.User;
import com.kino.database.connector.SqliteDAO;

@Controller
public class BookingController {
	@Autowired
	
	private SqliteDAO sqliteDAO;
	
	private User getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();

		} else {
			userName = principal.toString();
		}
		List<User> user = sqliteDAO.getUserByLogin(userName);
		if (user.size() == 0) {
			return null;
		}
		return user.get(0);
	}
	
	@RequestMapping(value="/booking_select", method = RequestMethod.POST)
	public String displayPOST(@RequestParam(value="seat",required=false) String seats[],@RequestParam(value="seanceID",required=false) String seanceID,Map<String, Object> model) {
		if(seats!=null&&seanceID!=null){
			int[] idArray=new int[seats.length];
			try{
				for(int i=0;i<seats.length;i++){
					idArray[i]=Integer.parseInt(seats[i]);
				}
				if(sqliteDAO.canBook(idArray, Integer.parseInt(seanceID))){
					model.put("seats", sqliteDAO.getSeatListForIDArray(idArray));
					model.put("user", getPrincipal());
					model.put("seanceID", seanceID);
					return "booking_detail";
				}else{
					model.put("message", "Wybrane przez ciebie miejsca s¹ ju¿ zajête - wybierz inne i spróbuj ponownie");
				}
			}catch(NumberFormatException ex){
				model.put("message", "B³¹d w przekazanych parametrach");
			}
		}else{			
			model.put("seance", seanceID);
			return "redirect:/booking_select";
		}
		
		return "booking_select";
	}
	
	@RequestMapping(value="/booking_detail", method = RequestMethod.POST)
	public String displayDetailPOST(@RequestParam(value="seatID",required=false) String seatID[],
			@RequestParam(value="seanceID",required=false) String seanceID,
			@RequestParam(value="lowerPrice",required=false) boolean lowerPrice[],
			@RequestParam String action,
			Map<String, Object> model) {
		if(seatID!=null&&seanceID!=null){
			List<Booking> bookingList=new ArrayList<>();
			try{
				String userName="";
				if(getPrincipal()!=null){
					userName=getPrincipal().getLogin();
				}else{
					userName="anonymous";
				}
				String code=userName.substring(0, 3)+seanceID+(int)Math.floor((Math.random()*9000)+1000);
				boolean hasPaid=action.equals("Kup");
				for(int i=0;i<seatID.length;i++){
					Booking b=new Booking();
					b.setLogin(userName);
					b.setSeanceID(Integer.parseInt(seanceID));
					b.setSeatID(Integer.parseInt(seatID[i]));
					b.setLowerPrice(lowerPrice[i]);
					b.setHasPaid(hasPaid);
					b.setCode(code);
					bookingList.add(b);
				}
				if(sqliteDAO.canBookList(bookingList)){
					for(Booking b:bookingList){
						sqliteDAO.insertBooking(b,code);
						model.put("message", "Rezerwacja zakoñczona powodzeniem");
						model.put("code", code);
					}
				}else{
					model.put("message", "Wybrane przez ciebie miejsca s¹ ju¿ zajête - wybierz inne i spróbuj ponownie");
				}
			}catch(NumberFormatException ex){
				model.put("message", "B³¹d w przekazanych parametrach");
			}
		}
		return "booking_result";
	}
	
	@RequestMapping(value="/booking_select", method = RequestMethod.GET)
	public String displayGET(@RequestParam(value="seance",required=false) String seance, Map<String, Object> model) {
		if (getPrincipal() != null) {
			model.put("user", getPrincipal());
		}
		if(seance!=null){
			int seanceID=0;
			try{
				seanceID=Integer.parseInt(seance);
				List<Seat> seatList=sqliteDAO.getSeatListForSeance(seanceID);
				model.put("message", "Wybierz miejsca:");
				model.put("seanceID", seance);
				model.put("seats", seatList);
			}catch(NumberFormatException ex){
				model.put("message", "Nie rozpoznano id seansu");
			}
		}else{
			model.put("message", "Nie rozpoznano id seansu");			
		}
		return "booking_select";
	}
	
	@RequestMapping(value="/check_booking", method = RequestMethod.GET)
	public String displayCheckGET(@RequestParam(value="code",required=false) String code, Map<String, Object> model) {
		if (getPrincipal() != null) {
			model.put("user", getPrincipal());
		}
		if(code!=null){
			List<Booking> bookingList=sqliteDAO.getBookingForCode(code);
			if(!bookingList.isEmpty()){
				if(getPrincipal()!=null){
					boolean result=true;
					for(Booking b:bookingList){
						if(!b.getLogin().equals(getPrincipal().getLogin())){
							result=false;
						}
					}
					if(result){
						Seance seance=sqliteDAO.getSeanceByID(bookingList.get(0).getSeanceID()+"").get(0);
						String link="<p><a href=\"seance_detail?id="+seance.getID()+"\">Seans: "+seance.getTitle()+" "+seance.getStartTime()+"</a></p><p>Sala "+seance.getRoomNumber()+"</p>";
						model.put("link", link);
						List<String> booking=new ArrayList<>();
						for(int i=0;i<bookingList.size();i++){
							Seat seat=sqliteDAO.getSeatForId(bookingList.get(i).getSeatID());
							String s="<p>Rz¹d: "+seat.getRowNumber()+" Siedzenie: "+seat.getSeatNumber()+" Bilet: "+(bookingList.get(i).getLowerPrice()?"Ulgowy":"Normalny")+"<p>";
							booking.add(s);
						}
						model.put("booking", booking);
					}else{
						model.put("message", "Te rezerwacje nie nale¿¹ do ciebie");
					}
				}else{
					boolean result=true;
					for(Booking b:bookingList){
						if(!b.getLogin().equals("anonymous")){
							result=false;
						}
					}
					if(result){
						Seance seance=sqliteDAO.getSeanceByID(bookingList.get(0).getSeanceID()+"").get(0);
						List<String> booking=new ArrayList<>();
						String link="<p><a href=\"seance_detail?id="+seance.getID()+"\">Seans: "+seance.getTitle()+" "+seance.getStartTime()+"</a></p><p>Sala "+seance.getRoomNumber()+"</p>";
						model.put("link", link);
						for(int i=0;i<bookingList.size();i++){
							Seat seat=sqliteDAO.getSeatForId(bookingList.get(i).getSeatID());
							String s="<p>Rz¹d: "+seat.getRowNumber()+" Siedzenie: "+seat.getSeatNumber()+" Bilet: "+(bookingList.get(i).getLowerPrice()?"Ulgowy":"Normalny")+"<p>";
							booking.add(s);
						}
						model.put("booking", booking);
					}else{
						model.put("message", "Te rezerwacje nie nale¿¹ do ciebie");
					}
				}
			}else{
				model.put("message", "Nie rozpoznano kodu rezerwacji");
			}
		}else{
			model.put("message", "Nie podano kodu rezerwacji");
		}
		return "check_booking";
	}
	
	
	
	
}
