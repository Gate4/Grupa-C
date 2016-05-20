package com.kino.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kino.database.connector.SqliteDAO;

@Controller
public class HomeController {
	@Autowired
	
	private SqliteDAO sqliteDAO;
	@RequestMapping("/welcome")
	public String displayAll(Map<String, Object> model) {
		model.put("seances", sqliteDAO.getFutureSeances());
		model.put("movies", sqliteDAO.getAllMovies());
		String message = "Treœæ newsa ze Springa";
		model.put("message", message);
		return "welcome";
	}
	

}
