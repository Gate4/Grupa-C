package com.kino.Baa.Bazyy.DAO;

import java.util.List;
import java.util.Map;

public interface KlienciDAO 
{
	 void insert (Klienci klient);
	 
	 void deleteKlient (String login);
	 
	 List<Klienci> getKlientByLogin(String login);
	 
	 List<Klienci> getKlientByLogin();
}
