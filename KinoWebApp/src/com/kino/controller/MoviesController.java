package com.kino.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kino.database.DAO.Movie;
import com.kino.database.connector.SqliteDAO;

@Controller
public class MoviesController {
	@Autowired
	
	private SqliteDAO sqliteDAO;
	@RequestMapping(value="/movie_detail", method = RequestMethod.GET)
	public String displayAll(@RequestParam("title") String title,Map<String, Object> model) {
		List<Movie> lista=sqliteDAO.getMovieListByName(title);
		if(!lista.isEmpty()){
			model.put("movie", lista.get(0));
			model.put("seances", sqliteDAO.getFutureSeancesForTitle(title));
		}
		
		return "/movie_detail";
	}
	
}
