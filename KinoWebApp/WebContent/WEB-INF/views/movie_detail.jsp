<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<spring:url value="/resources/style.css" var="styleCSS" />
<link href="${styleCSS}" rel="stylesheet" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link href="${styleCSS}" rel="stylesheet" />
<title>Kino</title>
</head>

<body>
	<div id="page">
		<header>
			<h1 id="logo">
				<img src="resources/img/logo.png" alt="Kino Katowice">
			</h1>

			<nav class="clear">
				<ul>
					<li><a href='/KinoWebApp/'>Strona główna</a></li>
					<li><a href='welcome'>Repertuar</a></li>
					<li><a href='price_list?day=0'>Cennik</a></li>
					<li><a href='login'>Zaloguj</a></li>
				</ul>
			</nav>
		</header>


		<div id="content" role="main">
			<section class="primary">
				<section class="tile">
				<img src="resources/img/${movie.poster}.jpg" alt="${movie.title}">
				<h2>${movie.title}</h2>
				<p>Gatunek: <span>${movie.genre}</span></p>
				<p>Reżyseria: <span>${movie.direction}</span></p>
				<p>Scenariusz: <span>${movie.scenario}</span></p>
				<p>Czas trwania: <span>${movie.duration} min.</span></p>
				<p>Od lat: <span>${movie.pegi}</span></p>
				<p>Premiera: ${movie.releaseYear}</span></p>
				<p>Opis filmu:<br><span>${movie.description}</span></p>
				</section>
				
			</section>
		</div>

		<footer class="clear">
			<section class="footer">
				<p>Copyright © 2016 | Grupa C</p>
			</section>
		</footer>
	</div>

</body>

</html>