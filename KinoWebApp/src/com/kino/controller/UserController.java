package com.kino.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kino.database.DAO.Movie;
import com.kino.database.DAO.Seance;
import com.kino.database.DAO.User;
import com.kino.database.connector.SqliteDAO;

@Controller
public class UserController {
	@Autowired
	private SqliteDAO sqliteDAO;

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
		String result = "createSuccess";
		if (!user.getLogin().isEmpty() && !user.getPhone().isEmpty() && !user.getEmail().isEmpty()) {
			if (sqliteDAO.getUserByLogin(user.getLogin()).isEmpty()) {
				System.out.println("Rejestrujê usera " + user.getLogin());
				sqliteDAO.insertUser(user);
				model.addAttribute("message", user.getLogin());
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
