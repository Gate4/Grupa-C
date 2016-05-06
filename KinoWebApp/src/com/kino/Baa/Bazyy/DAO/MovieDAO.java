package com.kino.Baa.Bazyy.DAO;

import java.util.List;
import java.util.Map;



public interface MovieDAO 
{
	void insert (Movie film);
	
	void deleteFilm (String tytul);
	
	void findFilmByName (String tytul);
	
	void findFilmByType (String gatunek);
	
	List<Movie> getFilmListByName(String tytul);
	
	public Map<String, Integer> getStatFilmy();
	
	public List getAllMovies();
	
}
