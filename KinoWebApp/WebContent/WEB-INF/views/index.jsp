<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>

<spring:url value="/resources/style.css" var="styleCSS" />
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
					<li><a class="active" href='/KinoWebApp/'>Strona główna</a></li>
					<li><a href='welcome'>Repertuar</a></li>
					<li><a href='price_list?day=0'>Cennik</a></li>
					<li><a href='login'>Zaloguj</a></li>
				</ul>
			</nav>
		</header>


		<div id="content" role="main">
			<section class="primary">
				<section class="tile">
					Dear <strong>${user}</strong>, Welcome to Home Page.
					<a href="<c:url value="/logout" />">Logout</a>
			    	<div>
        				<sec:authorize access="hasRole('ADMIN')">
            				<label><a href="#">Edit this page</a> | This part is visible only to ADMIN</label>
        				</sec:authorize>
    				</div>
				</section>
				<section class="tile">
					<h2>Polecamy:</h2>
					<section class="poster">
						<a href='movie_detail?title=Commando'><img
							src="resources/img/p0.jpg" alt="Tytuł filmu 1"></a>
					</section>
					<section class="poster">
						<a href='movie_detail?title=Annabel'><img
							src="resources/img/p1.jpg" alt="Tytuł filmu 2"></a>
					</section>
					<section class="poster">
						<a href='movie_detail?title=Obecność'><img
							src="resources/img/p2.jpg" alt="Tytuł filmu 3"></a>
					</section>
					<section class="poster">
						<a href='movie_detail?title=Piła 7'><img
							src="resources/img/p3.jpg" alt="Tytuł filmu 4"></a>
					</section>
				</section>
			</section>
			<aside class="secondary">
				<h2>W najbliższym czasie:</h2>
				<c:forEach items="${seances}" var="seance">
					<section>
						<hr>
						<h2><a href='movie_detail?title=${seance.title}'>${seance.title}</a></h2>
						<ul><li><a href='seance_detail?id=${seance.ID}'>${seance.startTime}</a></li></ul>
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
