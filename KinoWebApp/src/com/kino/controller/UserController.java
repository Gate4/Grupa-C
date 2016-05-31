package com.kino.controller;

import java.util.List;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class UserController {
	@Autowired
	private SqliteDAO sqliteDAO;

	   private User getPrincipal(){
	        String userName = null;
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 
	        if (principal instanceof UserDetails) {
	            userName = ((UserDetails)principal).getUsername();
	           
	        } else {
	            userName = principal.toString();
	        }
	        List<User> user=sqliteDAO.getUserByLogin(userName);
	        if(user.size()==0){	        	
	        	return null;
	        }
	        return user.get(0);
	    }
	
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(ModelMap model) {
    	if(getPrincipal()!=null){
    		model.addAttribute("user", getPrincipal());    		
    	}
    	  
        List<Seance> seances=sqliteDAO.getFutureSeances();
        if(!seances.isEmpty()){
        	model.put("seances", seances);
        	model.put("latest", sqliteDAO.getMovieListByName(seances.get(0).getTitle()).get(0));
        }
        
        return "index";
    }
	
    @RequestMapping(value = "/user/user_show_profile", method = RequestMethod.GET)
    public String viewUserPanel(ModelMap model) {
    	if(getPrincipal()!=null){
    		model.addAttribute("user", getPrincipal());    		
    	}
    	  
        List<Seance> seances=sqliteDAO.getFutureSeances();
        if(!seances.isEmpty()){
        	model.put("seances", seances);
        	
        }
        
        return "user/user_show_profile";
    }
    
    

	@RequestMapping(value = "/user/user_show_profile", method = RequestMethod.POST)
	public String viewUserPanel(@RequestParam String action, 
			@ModelAttribute("userForm") User user, Model model) {
		String result = "redirect:/user/user_show_profile";
		User principal =getPrincipal();
		if (action.equals("Edytuj dane")) {
			result = "/user/user_edit_profile";
			model.addAttribute("userForm", sqliteDAO.getUserByLogin(getPrincipal().getLogin()).get(0));
					
		} else if (action.equals("Usun konto")) {
			sqliteDAO.deleteUser(principal.getLogin());
			SecurityContextHolder.clearContext();
		} else if (action.equals("Zapisz zmiany")) {
			user.setAuthorities(principal.getAuthorities());
			user.setLogin(principal.getLogin());
			user.setPassword(principal.getPassword());
			sqliteDAO.updateUserByLogin(principal.getLogin(),user);
		} 
		return result;
	}
    
    
    
    
    
    @RequestMapping(value = "/user/user_change_password", method = RequestMethod.GET)
    public String editUserPasswordPanel(ModelMap model) {
    	if(getPrincipal()!=null){
    		model.addAttribute("user", getPrincipal());    		
    	}
    	  
        List<Seance> seances=sqliteDAO.getFutureSeances();
        if(!seances.isEmpty()){
        	model.put("seances", seances);
        	
        }
        
        return "user/user_change_password";
    }
	
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

 	
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
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
				user.setAuthorities("ROLE_USER");
				System.out.println("Rejestruj� usera " + user.getAuthorities());				
				sqliteDAO.insertUser(user);				
				model.addAttribute("message", user.getLogin());
			} else {
				model.addAttribute("message", "Login jest ju� zaj�ty");
				result = "create";
			}
		} else {
			model.addAttribute("message", "Wype�nij wszystkie pole");
			result = "create";
		}
		return result;
	}

	

}
