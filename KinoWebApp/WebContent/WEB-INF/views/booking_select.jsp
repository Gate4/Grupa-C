<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>





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
					<h2>${message}</h2>
					<c:forEach items="${seats}" var="seat">
						<p>${seat}</p>
					</c:forEach>
					<form action="booking_select" method="post">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<table>
							<tr>
								<td></td>
								<c:forEach var="i" begin="1" end="15">
									<td><c:out value="${i}" /></td>
								</c:forEach>
							</tr>
							<c:forEach var="y" begin="1" end="10">
								<tr>
									<td><c:out value="${y}" /></td>
									<c:forEach var="x" begin="1" end="15">
										<td>
											<div class="checkBoxEnabled">
												<input type="checkbox" value="<c:out value="${x}"/>.<c:out value="${y}"/>" id="<c:out value="${x}"/>.<c:out value="${y}"/>" name="seat"/>
												<label for="<c:out value="${x}"/>.<c:out value="${y}"/>"></label>
											</div>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
						<input type="submit" value="Kup/zarezerwuj">
					</form>
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
