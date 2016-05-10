package com.kino.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import com.kino.Baa.Bazyy.DAO.Movie;
import com.kino.Baa.Bazyy.DAO.User;
import com.kino.Baa.Bazyy.connector.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class MainController {

	@RequestMapping(value = "/welcome")
	public ModelAndView displayAll(Map<String, Object> model) {
		ApplicationContext context = new ClassPathXmlApplicationContext("aa.xml");
		SqliteDAO sqliteDAO = (SqliteDAO) context.getBean("sqliteDAO");
		List<Movie> moviesList = sqliteDAO.getAllMovies();
		String output = "";
		int n = moviesList.size();
		for (int i = 0; i < n; i++) {
			String title = moviesList.get(i).getTitle();
			String genre = moviesList.get(i).getGenre();
			String description = moviesList.get(i).getDescription();
			String releaseYear = moviesList.get(i).getReleaseYear();
			String direction = moviesList.get(i).getDirection();
			String scenario = moviesList.get(i).getScenario();
			String pegi = moviesList.get(i).getPegi();
			String duration = moviesList.get(i).getDuration();
			output = output.concat("<section class=\"tile\"><img src=\"resources/img/poster1.jpg\" alt=\"" + title
					+ "\"><h2>" + title + "</h2><p>Gatunek: <span>" + genre
					+ "</span></p><p>Re¿yseria: <span>"+direction+"</span></p><p>Scenariusz: <span>"+scenario+"</span></p><p>Czas trwania: <span>"+duration+" min.</span></p><p>Od lat: <span>"+pegi+"</span></p><p>Premiera: <span>"
					+ releaseYear + "</span></p><p>Opis filmu:<br> <span>" + description + "</span></p></section>");
		}
		String message = "Treœæ newsa ze Springa";
		model.put("message", message);
		model.put("kafelek", output);
		return new ModelAndView("welcome", model);
	}

	@RequestMapping("/login")
	public ModelAndView login(Map<String, Object> model) {

		return new ModelAndView("login", model);
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String viewRegistration(Map<String, Object> model) {
		User userForm = new User();
		model.put("userForm", userForm);
		return "create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String processRegistration(@ModelAttribute("userForm") User user, Model model) {
		System.out.println(user.getLogin());
		System.out.println(user.getEmail());
		System.out.println(user.getPhone());

		String result = "createSuccess";
		if (!user.getLogin().isEmpty() && !user.getPhone().isEmpty() && !user.getEmail().isEmpty()) {
			ApplicationContext context = new ClassPathXmlApplicationContext("aa.xml");
			SqliteDAO sqliteDAO = (SqliteDAO) context.getBean("sqliteDAO");
			if (sqliteDAO.getUserByLogin(user.getLogin()).isEmpty()) {
				System.out.println("Rejestrujê usera " + user.getLogin());
				sqliteDAO.insertUser(user);
			} else {
				model.addAttribute("message", "Login jest ju¿ zajêty");
				result = "create";
			}
		} else {
			model.addAttribute("message", "Wype³nij wszystkie pola");
			result = "create";
		}
		return result;
	}

}
