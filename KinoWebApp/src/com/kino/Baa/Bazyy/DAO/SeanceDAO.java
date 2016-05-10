package com.kino.Baa.Bazyy.DAO;

import java.util.List;
import java.util.Map;

public interface SeanceDAO 
{
	void insert (Seance seans);
	
	void deleteSeans (String czas);
	
	List<Seance> getSeansByTytul(String tytul);
	 
	List<Seance> getSeansByTytul();
}
