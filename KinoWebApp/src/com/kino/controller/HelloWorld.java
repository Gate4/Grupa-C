package com.kino.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




import com.kino.Baa.Bazyy.connector.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Map;


@Controller
public class HelloWorld {
	
	@RequestMapping("/welcome")
	public ModelAndView helloWorld(Map<String, Object> model) {
		ApplicationContext context = new ClassPathXmlApplicationContext("aa.xml");
		SqliteDAO sqliteDAO = (SqliteDAO) context.getBean("sqliteDAO");
		String message="Treœæ newsa ze Springa";
		String tytul=sqliteDAO.getFilmListByName("Commando").get(0).getTytul();
		String gatunek=sqliteDAO.getFilmListByName("Commando").get(0).getGatunek();
		String opis=sqliteDAO.getFilmListByName("Commando").get(0).getOpis();
		String rok=sqliteDAO.getFilmListByName("Commando").get(0).getRok();
		model.put("message", message);
		model.put("tytul", tytul);
		model.put("gatunek", gatunek);
		model.put("opis", opis);
		model.put("rok", rok);
		return new ModelAndView("welcome", model);
	}
 
}
