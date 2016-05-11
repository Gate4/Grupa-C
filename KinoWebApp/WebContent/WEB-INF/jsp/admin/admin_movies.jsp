<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>


<spring:url value="/resources/style.css" var="styleCSS" />
<link href="${styleCSS}" rel="stylesheet" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link href="${styleCSS}" rel="stylesheet" />
<title>Kino - panel administratora</title>
</head>

<body>
	<div id="page">
		<header>
			<h1 id="logo">Panel administratora</h1>

			<nav class="clear">
				<ul>
					<li><a class="active" href='admin_movies.html'>Filmy</a></li>
					<li><a href='#'>Seanse</a></li>
					<li><a href='#'>Użytkownicy</a></li>
					<li><a href='http://localhost:8080/KinoWebApp/welcome.jsp'>Strona
							główna</a></li>
				</ul>
			</nav>
		</header>


		<div id="content" role="main">
			<section class="primary">

				
					<c:forEach items="${movies}" var="movie">
					<section class="tile">
							<p>${employee.id}</p>
							<p>Tytuł: ${movie.title}</p>
							<p>Gatunek: ${movie.genre}</p>
							<p>Premiera: ${movie.releaseYear}</p>
							<p>Opis: ${movie.description}</p>
							<p>Reżyser: ${movie.direction}</p>
							<p>Scenariusz: ${movie.scenario}</p>
							<p>Od lat: ${movie.pegi}</p>
							<p>Czas trwania: ${movie.duration}</p>
							<p><input type="submit" name="action" value="Edytuj"/><input type="submit" name="action" value="Usuń"/></p>
						</section>
					</c:forEach>
				
			</section>
		</div>
	</div>
</body>
</html>
