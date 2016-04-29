package com.kino.Baa.Bazyy.DAO;

import java.util.List;
import java.util.Map;

public interface SeansyDAO 
{
	void insert (Seansy seans);
	
	void deleteSeans (String czas);
	
	List<Seansy> getSeansByTytul(String tytul);
	 
	List<Seansy> getSeansByTytul();
}
