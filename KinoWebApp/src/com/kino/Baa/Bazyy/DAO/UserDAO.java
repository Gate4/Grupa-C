package com.kino.Baa.Bazyy.DAO;

import java.util.List;
import java.util.Map;

public interface UserDAO 
{
	 void insert (User klient);
	 
	 void deleteKlient (String login);
	 
	 List<User> getKlientByLogin(String login);
	 
	 List<User> getKlientByLogin();
}
