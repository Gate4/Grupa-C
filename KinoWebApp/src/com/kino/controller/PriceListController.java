package com.kino.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kino.database.connector.SqliteDAO;

@Controller
public class PriceListController {
	@Autowired
	
	private SqliteDAO sqliteDAO;
	@RequestMapping(value="/price_list", method = RequestMethod.GET)
	public String displayAll(@RequestParam("day") String day,Map<String, Object> model) {
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
