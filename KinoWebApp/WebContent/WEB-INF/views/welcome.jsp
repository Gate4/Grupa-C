<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>





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
					<li><a class="active" href='welcome'>Repertuar</a></li>
					<li><a href='price_list?day=0'>Cennik</a></li>
					<li><a href='login'>Zaloguj</a></li>
				</ul>
			</nav>
		</header>


		<div id="content" role="main">
			<section class="primary">
			
				<c:forEach items="${movies}" var="movie">
				<a href="movie_detail?title=${movie.title}">
				<section class="tile_list">
				<img src="resources/img/${movie.poster}.jpg" alt="">
				<h2>${movie.title}</h2>
				</section>
				</a>
				</c:forEach>
			</section>

			<aside class="secondary">

			
			<h2>W najbliższym czasie:</h2>
			<c:forEach items="${seances}" var="seance">
				<section>
				<hr>
				<h2>${seance.title}</h2>
				<p>${seance.startTime}</p>
				</section>
			</c:forEach>
			</aside>
		</div>

		<footer class="clear">
			<section class="footer">
				<p>Copyright © 2016 | Grupa C</p>
			</section>
		</footer>
	</div>

</body>

</html>
