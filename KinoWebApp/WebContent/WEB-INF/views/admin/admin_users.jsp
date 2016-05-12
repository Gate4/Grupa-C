<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
					<li><a href='admin_movies.html'>Filmy</a></li>
					<li><a href='admin_seances.html'>Seanse</a></li>
					<li><a class="active" href='admin_users.html'>Użytkownicy</a></li>
					<li><a href='http://localhost:8080/KinoWebApp/welcome'>Strona
							główna</a></li>
				</ul>
			</nav>
		</header>


		<div id="content" role="main">
			<section class="primary">
				<form action="admin_users.html" method="POST">
					<input type="hidden" name="login" value="" readonly /> <input
						type="submit" name="action" value="Dodaj nowy"/>
				</form>
				<c:forEach items="${users}" var="user">
					<section class="tile">
						<form action="admin_users.html" method="POST">
							<table>
								<tr>
									<td>Login:</td>
									<td><input type="text" name="login" value="${user.login}"
										readonly />
								</tr>
								<tr>
									<td>Hasło:</td>
									<td><input type="password" value="${user.password}" readonly /></td>
								</tr>
								<tr>
									<td>Numer telefonu:</td>
									<td><input type="text" value="${user.phone}"
										readonly /></td>
								</tr>
								<tr>
									<td>E-mail:</td>
									<td><input type="text" value="${user.email}" readonly /></td>
								</tr>
								<tr>
									<td>Imię:</td>
									<td><input type="text" value="${user.name}" readonly /></td>
								</tr>
								<tr>
									<td>Nazwisko:</td>
									<td><input type="text" value="${user.surname}" readonly /></td>
								</tr>
								<tr>
									<td><input type="submit" name="action" value="Edytuj" /><input
										type="submit" name="action" value="Skasuj" /></td>
								</tr>
							</table>
						</form>
					</section>
				</c:forEach>

			</section>
		</div>
	</div>
</body>
</html>
