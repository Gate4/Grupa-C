package com.kino.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import com.kino.Baa.Bazyy.DAO.Filmy;
import com.kino.Baa.Bazyy.DAO.Klienci;
import com.kino.Baa.Bazyy.connector.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class HelloWorld {

	@RequestMapping(value="/welcome")
	/*
	 * public ModelAndView helloWorld(Map<String, Object> model) {
	 * ApplicationContext context = new
	 * ClassPathXmlApplicationContext("aa.xml"); SqliteDAO sqliteDAO =
	 * (SqliteDAO) context.getBean("sqliteDAO"); String message=
	 * "Treœæ newsa ze Springa"; String
	 * tytul=sqliteDAO.getFilmListByName("Commando").get(0).getTytul(); String
	 * gatunek=sqliteDAO.getFilmListByName("Commando").get(0).getGatunek();
	 * String opis=sqliteDAO.getFilmListByName("Commando").get(0).getOpis();
	 * String rok=sqliteDAO.getFilmListByName("Commando").get(0).getRok();
	 * model.put("message", message); model.put("tytul", tytul);
	 * model.put("gatunek", gatunek); model.put("opis", opis); model.put("rok",
	 * rok); return new ModelAndView("welcome", model); }
	 */
	public ModelAndView displayAll(Map<String, Object> model) {
		ApplicationContext context = new ClassPathXmlApplicationContext("aa.xml");
		SqliteDAO sqliteDAO = (SqliteDAO) context.getBean("sqliteDAO");
		String message = "Treœæ newsa ze Springa";
		List<Filmy> moviesList = sqliteDAO.getAllMovies();
		String output="";
		int n=moviesList.size();
		for(int i=0;i<n;i++){
			String title = moviesList.get(i).getTitle();
			String genre = moviesList.get(i).getGenre();
			String description = moviesList.get(i).getDescription();
			String year = moviesList.get(i).getYear();
			output=output.concat("<section class=\"tile\"><img src=\"resources/img/poster1.jpg\" alt=\""+title+"\"><h2>"+title+"</h2><p>Gatunek: <span>"+genre+"</span></p><p>Re¿yseria: <span>nieznany</span></p><p>Scenariusz: <span>nieznany</span></p><p>Czas trwania: <span>nieznany</span></p><p>Od lat: <span>nieznany</span></p><p>Premiera: <span>"+year+"</span></p><p>Opis filmu:<br> <span>"+description+"</span></p></section>");
		}	
		model.put("message", message);
		model.put("kafelek",output);
		return new ModelAndView("welcome", model);
	}
	
	@RequestMapping("/login")
	public ModelAndView login(Map<String, Object> model) {
		
		return new ModelAndView("login", model);
	}
	
	@RequestMapping(value="/create",method = RequestMethod.GET)
	public String viewRegistration(Map<String, Object> model) {
        Klienci userForm = new Klienci();
        model.put("userForm", userForm);
        return "create";
    }
	
	@RequestMapping(value="/create",method = RequestMethod.POST)
	public String processRegistration(@ModelAttribute("userForm") Klienci user,
            Map<String, Object> model) {
		System.out.println(user.getLogin());
        System.out.println(user.getEmail());
        System.out.println(user.getTelefon());
        String result="createSuccess";
        if(!user.getLogin().isEmpty()){
        	ApplicationContext context = new ClassPathXmlApplicationContext("aa.xml");
    		SqliteDAO sqliteDAO = (SqliteDAO) context.getBean("sqliteDAO");
        	if(sqliteDAO.getKlientByLogin(user.getLogin()).isEmpty()){
        		System.out.println("Rejestrujê usera "+user.getLogin());
        		sqliteDAO.insert(user);
        	}else{
        		result="createFail";
        	}
        }else{
        	result="createFail";
        }
        return result;
    }

}
