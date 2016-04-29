package com.kino.Baa.Bazyy.connector;


import com.kino.Baa.Bazyy.DAO.Filmy;
import com.kino.Baa.Bazyy.DAO.FilmyDAO;
import com.kino.Baa.Bazyy.DAO.Klienci;
import com.kino.Baa.Bazyy.DAO.KlienciDAO;
import com.kino.Baa.Bazyy.DAO.Seansy;
import com.kino.Baa.Bazyy.DAO.SeansyDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;



@Component("sqliteDAO") 
public class SqliteDAO implements KlienciDAO, FilmyDAO, SeansyDAO
{
	
	private NamedParameterJdbcTemplate jdbcTemplate ;

	@Autowired
	public void setDataSource(DataSource datasource)
	{
		this.jdbcTemplate=new NamedParameterJdbcTemplate(datasource);
	}
	

    

	public void insert(Filmy film)
	{
		String sql = "insert into Filmy (tytul,gatunek,rok,opis) VALUES (:tytul, :gatunek, :rok, :opis)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("tytul",film.getTytul());
		params.addValue("gatunek",film.getGatunek());
		params.addValue("rok",film.getRok());
		params.addValue("opis",film.getOpis());
		jdbcTemplate.update(sql, params);
	}
	
//	public void insert(Filmy film) 
//	{
//		
//		String sql = "insert into Filmy (tytul,gatunek,rok,opis) VALUES (?,?,?,?)";
//		jdbcTemplate.update(sql, new Object[]{film.getTytul(),film.getGatunek(),film.getRok(),film.getOpis()});		
//	}
	
	public void deleteFilm(String tytul)
	{
		String sql = "delete from Filmy where tytul=?";		
	    jdbcTemplate.getJdbcOperations().update(sql,tytul);
	}
	
	public void insert(Klienci klient) 
	{
		String sql = "insert into Klienty (login,telefon,email) VALUES (?,?,?)";
		jdbcTemplate.getJdbcOperations().update(sql, new Object[]{klient.getLogin(),klient.getTelefon(),klient.getEmail()});		
		

	}
	
	public void deleteKlient(String login) 
	{		
		String sql = "delete from Klienty where login=?";
		int result = jdbcTemplate.getJdbcOperations().update(sql,login);	
	}



	public void insert(Seansy seans) 
	{
		String sql = "insert into Seansy (czas,sala,tytul) VALUES (?,?,?)";
		jdbcTemplate.getJdbcOperations().update(sql, new Object[]{seans.getCzas(),seans.getSala(),seans.getTytul()});
		
	}



	public void deleteSeans(String czas) 
	{
		String sql = "delete from Seansy where czas=?";
		int result = jdbcTemplate.getJdbcOperations().update(sql,czas);			
	}

	public void findFilmByName(String tytul) 
	{
		String sql = "select * from Filmy where tytul=? ";
		List<Map<String, Object>>  lista =  jdbcTemplate.getJdbcOperations().queryForList(sql,tytul);
		for(Object li: lista)
		{
			System.out.println(li.toString());
		}
		
	}

	public void findFilmByType(String gatunek) 
	{
		String sql = "select * from Filmy where gatunek=? ";
		List<Map<String, Object>>  lista =  jdbcTemplate.getJdbcOperations().queryForList(sql,gatunek);
		for(Object li: lista)
		{
			System.out.println(li.toString());
		}
		
	}

	
	private static final class FilmRowMapper implements RowMapper<Filmy> {

		public Filmy mapRow(ResultSet rs, int rowNum) throws SQLException {
			Filmy film = new Filmy();
			film.setGatunek(rs.getString("gatunek"));
			film.setTytul(rs.getString("tytul"));
			film.setRok(rs.getString("rok"));
			film.setOpis(rs.getString("opis"));
			return film;
		}

	}
	
	private static final class SeansRowMapper implements RowMapper<Seansy> {

		public Seansy mapRow(ResultSet rs, int rowNum) throws SQLException {
			Seansy seans = new Seansy();
			seans.setCzas(rs.getString("czas"));
			seans.setSala(rs.getString("sala"));
			seans.setTytul(rs.getString("tytul"));
			return seans;
		}

	}
	private static final class KlientRowMapper implements RowMapper<Klienci> {

		public Klienci mapRow(ResultSet rs, int rowNum) throws SQLException {
			Klienci klient = new Klienci();
			klient.setEmail(rs.getString("email"));
			klient.setLogin(rs.getString("login"));
			klient.setTelefon(rs.getString("telefon"));
			return klient;
		}

	}


	public Map<String, Integer> getStatFilmy() 
	{
		String sql = "select tytul, count(*) as count from Filmy group by tytul";

		return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, Integer>>() {

			public Map<String, Integer> extractData(ResultSet rs) throws SQLException {
				Map<String, Integer> map = new TreeMap<String, Integer>();
				while (rs.next()) {
					String tytul = rs.getString("tytul");
					int count = rs.getInt("count");
					map.put(tytul, count);
					System.out.println("Tytul filmu:" + tytul);
				}
				return map;
			};

		});
		
	}

	public List<Filmy> getFilmListByName(String tytul) 
	{
		String sql = "select * from Filmy where upper(tytul) like :tytul";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("tytul",  "%" + tytul.toUpperCase() + "%");
		List<Filmy> frm =  jdbcTemplate.query(sql,params, new FilmRowMapper ());
	    
	    for(Filmy li : frm)
	    {
	    	System.out.println("Tytul: " + li.getTytul());
	    }
	    return jdbcTemplate.query(sql,params, new FilmRowMapper ());

		
	}


	public List<Seansy> getSeansByTytul(String tytul) 
	{
		String sql = "select * from Seansy where upper(tytul) like :tytul";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("tytul", "%" + tytul.toUpperCase() + "%");
		List<Seansy> frm = jdbcTemplate.query(sql,params,new SeansRowMapper());
		
		for(Seansy seans : frm)
		{
			System.out.println("Czas seansu " + seans.getTytul() + " w salie " + seans.getSala() + ": " + seans.getCzas());
		}
		return jdbcTemplate.query(sql, params,new SeansRowMapper());
		
	}




	public List<Seansy> getSeansByTytul()
	{
		String sql = "select * from Seansy ";
		
		List<Seansy> frm = jdbcTemplate.query(sql,new SeansRowMapper());
		
		for(Seansy seans : frm)
		{
			System.out.println("Czas seansu " + seans.getTytul() + "w salie " + seans.getSala() + "w : " + seans.getCzas());
		}
		return jdbcTemplate.query(sql,new SeansRowMapper());
	}




	public List<Klienci> getKlientByLogin(String login) {
		
		String sql = "select * from Klienty where upper(login) like :login";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("login", login.toUpperCase());
		List<Klienci> frm = jdbcTemplate.query(sql,params,new KlientRowMapper());
		
		for(Klienci klient : frm)
		{
			System.out.println("Klient: " + klient.getLogin() + " ma email: " + klient.getEmail()  + " a numer telefoniczny " + klient.getTelefon());
		}
		return jdbcTemplate.query(sql, params,new KlientRowMapper());
	}




	public List<Klienci> getKlientByLogin() 
	{
		
		String sql = "select * from Klienty ";
		
		List<Klienci> frm = jdbcTemplate.query(sql,new KlientRowMapper());
		
		for(Klienci klient : frm)
		{
			System.out.println("Klient: " + klient.getLogin() + " ma email: " + klient.getEmail()  + " a numer telefoniczny " + klient.getTelefon());
		}
		return jdbcTemplate.query(sql,new KlientRowMapper());
	}

	 
		
	}



