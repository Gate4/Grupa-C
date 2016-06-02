package com.kino.controller;

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

import com.kino.database.DAO.Seance;
import com.kino.database.DAO.User;
import com.kino.database.connector.SqliteDAO;

@Controller
public class PriceListController {
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
	
	
	
	@RequestMapping(value="/price_list", method = RequestMethod.GET)
	public String displayAll(@RequestParam("day") String day,Map<String, Object> model) {
		
		if (getPrincipal() != null) {
			model.put("user", getPrincipal());
		}
		
		List<Seance> seances = sqliteDAO.getFutureSeances();
		if (!seances.isEmpty()) {
			model.put("seances", seances);

		}
		
		String[] days={"","Poniedzia³ek","Wtorek","Œroda","Czwartek","Pi¹tek","Sobota","Niedziela"};
		int dayInt=0;
		model.put("dayNames",days);
		try{
			dayInt=Integer.parseInt(day);
		}catch(NumberFormatException ex){	
		}	
		if(dayInt>0&&dayInt<8){
			model.put("prices", "<p><h3>"+days[dayInt]+"</h3></p><p>Normalna cena: "+sqliteDAO.getNormalPriceForDay(dayInt)+" z³</p><p>Ni¿sza cena: "+sqliteDAO.getLowerPriceForDay(dayInt)+" z³</p>");
		}		
		return "/price_list";
	}
	
}
