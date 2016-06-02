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
import com.kino.database.connector.SqliteDAO;

@Controller
public class SeanceController {
	@Autowired
	
	private SqliteDAO sqliteDAO;
	
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();       
        } else {
            userName = principal.toString();
        }
        return userName;
    }
	
	
	@RequestMapping(value="/seance_detail", method = RequestMethod.GET)
	public String displayDetailGET(@RequestParam(value="id",required=false) String id, Map<String, Object> model) {
		if(id!=null){
			try{
				Seance seance=sqliteDAO.getSeanceByID(id).get(0);
				String[] time=seance.getStartTime().split(" ");
				model.put("date",time[0]);
				model.put("time",time[1]);
				model.put("movie",sqliteDAO.getMovieListByName(seance.getTitle()).get(0));
				model.put("seance",seance);
			}catch(Exception ex){
				model.put("message", "B³¹d atrybutu");
			}
		}		
		return "seance_detail";
	}
	
	@RequestMapping(value="/seances", method = RequestMethod.GET)
	public String displaySeancesGET(@RequestParam(value="date",required=false) String date,Map<String, Object> model) {
		model.put("days", sqliteDAO.getWeekList());	
		model.put("seances", sqliteDAO.getSeanceListForDate(date));
		return "seances";
	}
}
