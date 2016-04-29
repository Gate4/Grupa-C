package com.kino.Baa.Bazyy.DAO;

import java.util.List;
import java.util.Map;



public interface FilmyDAO 
{
	void insert (Filmy film);
	
	void deleteFilm (String tytul);
	
	void findFilmByName (String tytul);
	
	void findFilmByType (String gatunek);
	
	List<Filmy> getFilmListByName(String tytul);
	
	public Map<String, Integer> getStatFilmy();
	
	
	
}
