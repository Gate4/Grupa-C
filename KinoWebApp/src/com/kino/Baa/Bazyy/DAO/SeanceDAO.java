package com.kino.Baa.Bazyy.DAO;

import java.util.List;
import java.util.Map;

public interface SeanceDAO 
{
	void insertSeance (Seance seans);
	
	void deleteSeance (String czas);
	
	List<Seance> getSeanceByTitle(String tytul);
	 
	List<Seance> getSeanceByID(String ID);
	
	List<Seance> getAllSeances();
}
