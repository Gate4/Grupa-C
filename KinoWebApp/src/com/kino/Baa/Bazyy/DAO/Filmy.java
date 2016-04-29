package com.kino.Baa.Bazyy.DAO;

public class Filmy 
{
	private String tytul;
	
	private String gatunek;
	
	private String rok;
	
	private String opis;

	public String getTitle() 
	{
		return tytul;
	}

	public void setTytul(String tytul) 
	{
		this.tytul = tytul;
	}

	public String getGenre() 
	{
		return gatunek;
	}

	public void setGatunek(String gatunek)
{
		this.gatunek = gatunek;
	}

	public String getYear() 
	{
		return rok;
	}

	public void setRok(String rok) 
	{
		this.rok = rok;
	}

	public String getDescription() 
	{
		return opis;
	}

	public void setOpis(String opis) 
	{
		this.opis = opis;
	}
	
}
