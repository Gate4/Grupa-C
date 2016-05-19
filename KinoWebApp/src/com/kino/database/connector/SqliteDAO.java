package com.kino.database.connector;

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

import com.kino.database.DAO.Movie;
import com.kino.database.DAO.MovieDAO;
import com.kino.database.DAO.PriceList;
import com.kino.database.DAO.PriceListDAO;
import com.kino.database.DAO.Seance;
import com.kino.database.DAO.SeanceDAO;
import com.kino.database.DAO.User;
import com.kino.database.DAO.UserDAO;

@Component("sqliteDAO")
public class SqliteDAO implements UserDAO, MovieDAO, SeanceDAO,PriceListDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource datasource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
	}

	public void insertMovie(Movie film) {
		String sql = "insert into Movie (title,genre,releaseYear,description,direction,scenario,pegi,duration,poster) VALUES (:title, :genre, :releaseYear, :description, :direction, :scenario, :pegi, :duration, :poster)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", film.getTitle());
		params.addValue("genre", film.getGenre());
		params.addValue("releaseYear", film.getReleaseYear());
		params.addValue("description", film.getDescription());
		params.addValue("direction", film.getDirection());
		params.addValue("scenario", film.getScenario());
		params.addValue("pegi", film.getPegi());
		params.addValue("duration", film.getDuration());
		params.addValue("poster", film.getPoster());
		jdbcTemplate.update(sql, params);
	}
	
	public void updateMovieByTitle(String title,Movie film){
		String sql = "update Movie SET genre=?,releaseYear=?,description=?,direction=?,scenario=?,pegi=?,duration=?,poster=? where title=?";
		jdbcTemplate.getJdbcOperations().update(sql, new Object[]{film.getGenre(),film.getReleaseYear(),film.getDescription(),film.getDirection(),film.getScenario(),film.getPegi(),film.getDuration(),film.getPoster(),title});
	}
	
	public void insertOrReplaceMovie(Movie film){
		if(getMovieListByName(film.getTitle()).isEmpty()){
			insertMovie(film);
		}else{
			updateMovieByTitle(film.getTitle(), film);
		}
	}
	
	

	// public void insert(Movie film)
	// {
	//
	// String sql = "insert into Movie (tytul,gatunek,rok,opis) VALUES
	// (?,?,?,?)";
	// jdbcTemplate.update(sql, new
	// Object[]{film.getTitle(),film.getGatunek(),film.getRok(),film.getOpis()});
	// }

	public void deleteMovie(String title) {
		String sql = "delete from Movie where title=?";
		jdbcTemplate.getJdbcOperations().update(sql, title);
	}

	public void insertUser(User user) {
		String sql = "insert into User (login,phone,email, password, name, surname) VALUES (?,?,?,?,?,?)";
		jdbcTemplate.getJdbcOperations().update(sql, new Object[] { user.getLogin(), user.getPhone(), user.getEmail(),
				user.getPassword(), user.getName(), user.getSurname() });

	}

	public void deleteUser(String login) {
		String sql = "delete from User where login=?";
		int result = jdbcTemplate.getJdbcOperations().update(sql, login);
	}
	
	public void insertOrReplaceUser(User user){
		if(getUserByLogin(user.getLogin()).isEmpty()){
			insertUser(user);
		}else{
			updateUserByLogin(user.getLogin(), user);
		}
	}
	
	public void updateUserByLogin(String login,User user){
		String sql = "update User SET password=?,phone=?,email=?,name=?,surname=? where login=?";
		jdbcTemplate.getJdbcOperations().update(sql, new Object[]{user.getPassword(),user.getPhone(),user.getEmail(),user.getName(),user.getSurname(),login});
	}

	public void insertSeance(Seance seance) {
		String sql = "insert into Seance (start_time,roomNumber,title) VALUES (?,?,?)";
		jdbcTemplate.getJdbcOperations().update(sql,
				new Object[] {seance.getStartTime(),seance.getRoomNumber(), seance.getTitle()});

	}

	public void deleteSeance(String id) {
		String sql = "delete from Seance where id=?";
		int result = jdbcTemplate.getJdbcOperations().update(sql, id);
	}
	
	public void updateSeanceByID(String ID,Seance seans){
		String sql = "update Seance SET start_time=?,roomNumber=?,title=? where ID=?";
		jdbcTemplate.getJdbcOperations().update(sql, new Object[]{seans.getStartTime(),seans.getRoomNumber(),seans.getTitle(),ID});
	}
	
	public void insertOrReplaceSeance(Seance seans){
		if(getSeanceByID(seans.getID()).isEmpty()){
			insertSeance(seans);
		}else{
			updateSeanceByID(seans.getID(), seans);
		}
	}

	public void findMovieByName(String title) {
		String sql = "select * from Movie where title=? ";
		List<Map<String, Object>> list = jdbcTemplate.getJdbcOperations().queryForList(sql, title);
		for (Object li : list) {
			System.out.println(li.toString());
		}

	}

	public void findMovieByType(String genre) {
		String sql = "select * from Movie where genre=? ";
		List<Map<String, Object>> list = jdbcTemplate.getJdbcOperations().queryForList(sql, genre);
		for (Object li : list) {
			System.out.println(li.toString());
		}

	}

	private static final class MovieRowMapper implements RowMapper<Movie> {

		public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
			Movie movie = new Movie();
			movie.setGenre(rs.getString("genre"));
			movie.setTitle(rs.getString("title"));
			movie.setReleaseYear(rs.getString("releaseYear"));
			movie.setDescription(rs.getString("description"));
			movie.setDirection(rs.getString("direction"));
			movie.setScenario(rs.getString("scenario"));
			movie.setPegi(rs.getString("pegi"));
			movie.setDuration(rs.getString("duration"));
			movie.setPoster(rs.getString("poster"));
			return movie;
		}

	}

	private static final class SeanceRowMapper implements RowMapper<Seance> {

		public Seance mapRow(ResultSet rs, int rowNum) throws SQLException {
			Seance seance = new Seance();
			seance.setID(rs.getString("id"));
			seance.setStartTime(rs.getString("start_time"));
			seance.setRoomNumber(rs.getString("roomNumber"));
			seance.setTitle(rs.getString("title"));
			return seance;
		}

	}

	private static final class UserRowMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setEmail(rs.getString("email"));
			user.setLogin(rs.getString("login"));
			user.setPhone(rs.getString("phone"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setSurname(rs.getString("surname"));
			return user;
		}

	}
	
	private static final class PriceListRowMapper implements RowMapper<PriceList>{

		public PriceList mapRow(ResultSet rs, int rowNum) throws SQLException {
			PriceList pl=new PriceList();
			pl.setDay(rs.getInt("id"));
			pl.setLowerPrice(rs.getFloat("lowerPrice"));
			pl.setNormalPrice(rs.getFloat("normalPrice"));
			return pl;
		}
		
	}

	public Map<String, Integer> getStatMovie() {
		String sql = "select title, count(*) as count from Movie group by title";

		return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, Integer>>() {

			public Map<String, Integer> extractData(ResultSet rs) throws SQLException {
				Map<String, Integer> map = new TreeMap<String, Integer>();
				while (rs.next()) {
					String title = rs.getString("title");
					int count = rs.getInt("count");
					map.put(title, count);
					System.out.println("Tytul filmu:" + title);
				}
				return map;
			};

		});

	}

	public List<Movie> getMovieListByName(String title) {
		String sql = "select * from Movie where upper(title) like :title";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", "%" + title + "%");
		List<Movie> frm = jdbcTemplate.query(sql, params, new MovieRowMapper());

		for (Movie li : frm) {
			System.out.println("Tytul: " + li.getTitle());
		}
		return jdbcTemplate.query(sql, params, new MovieRowMapper());
	}

	public List<Movie> getAllMovies() {
		String sql = "select * from Movie order by title";
		return jdbcTemplate.query(sql, new MovieRowMapper());
	}
	
	

	public List<Seance> getSeanceByTitle(String title) {
		String sql = "select * from Seance where upper(title) like :title";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", "%" + title.toUpperCase() + "%");
		List<Seance> frm = jdbcTemplate.query(sql, params, new SeanceRowMapper());

		for (Seance seans : frm) {
			System.out.println(
					"Czas seansu " + seans.getTitle() + " w salie " + seans.getRoomNumber());
		}
		return jdbcTemplate.query(sql, params, new SeanceRowMapper());
	}
	
	public List<Seance> getSeanceByID(String ID) {
		String sql="select * from Seance where ID=:ID";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ID", ID);
		return jdbcTemplate.query(sql, params, new SeanceRowMapper());
	}

	public List<Seance> getAllSeances() {
		String sql = "select * from Seance ";
		return jdbcTemplate.query(sql, new SeanceRowMapper());
	}

	public List<User> getUserByLogin(String login) {

		String sql = "select * from User where upper(login) like :login";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("login", login.toUpperCase());
		List<User> frm = jdbcTemplate.query(sql, params, new UserRowMapper());

		for (User user : frm) {
			System.out.println("Klient: " + user.getLogin() + " ma email: " + user.getEmail()
					+ " a numer telefoniczny " + user.getPhone());
		}
		return jdbcTemplate.query(sql, params, new UserRowMapper());
	}

	public List<User> getAllUsers() {

		String sql = "select * from User ";

		List<User> frm = jdbcTemplate.query(sql, new UserRowMapper());

		for (User klient : frm) {
			System.out.println("Klient: " + klient.getLogin() + " ma email: " + klient.getEmail()
					+ " a numer telefoniczny " + klient.getPhone());
		}
		return jdbcTemplate.query(sql, new UserRowMapper());
	}
	
	


	public double getNormalPriceForDay(int day) {
		String sql="select * from Prices";
		return jdbcTemplate.query(sql,new PriceListRowMapper()).get(day-1).getNormalPrice();
	}


	public double setNormalPriceForDay(int day, double price) {
		// TODO Auto-generated method stub
		return 0;
	}


	public double getLowerPriceForDay(int day) {
		/*String sql="select lowerPrice from Prices where id=:dzien";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("dzien", ""+day);
		return jdbcTemplate.query(sql, params, new PriceListRowMapper()).get(0).getLowerPrice();*/
		String sql="select * from Prices";
		return jdbcTemplate.query(sql,new PriceListRowMapper()).get(day-1).getLowerPrice();
	}


	public double setLowerPriceForDay(int day, double price) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Seance> getFutureSeances() {
		String sql = "select * from Seance where start_time>(select  datetime(CURRENT_TIMESTAMP, 'localtime')) order by start_time asc LIMIT 10";
		return jdbcTemplate.query(sql, new SeanceRowMapper());
	}



}
