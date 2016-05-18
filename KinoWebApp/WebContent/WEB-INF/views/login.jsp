<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
					<li><a href='#'>Strona główna</a></li>
					<li><a href='welcome'>Repertuar</a></li>
					<li><a href='#'>Cennik</a></li>
					<li><a class="active" href='login'>Zaloguj</a></li>
				</ul>
			</nav>
		</header>


		<div id="content" role="main">
			<section class="primary">
				<section class="tile">
				<h2>Logowanie</h2>
					<form method="POST">
						<table>
							<tr>
								<td>Login:</td>
								<td><input type="text" name="login"></td>
							</tr>
							<tr>
								<td>Hasło:</td>
								<td><input type="text" name="password"></td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" value="Zaloguj się" /></td>
							</tr>
						</table>
					</form>
					Nie masz konta? Zarejestruj się <a href='create'>tutaj</a>
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