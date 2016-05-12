<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html>
<html>
<head>


<spring:url value="/resources/style.css" var="styleCSS" />
<link href="${styleCSS}" rel="stylesheet" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script>
  $(function() {
    $( "#datepicker" ).datepicker();
  });
  </script>
<link href="${styleCSS}" rel="stylesheet" />
<title>Kino - panel administratora</title>
</head>

<body>
	<div id="page">
		<header>
			<h1 id="logo">Panel administratora</h1>
		</header>


		<div id="content" role="main">
			<section class="primary">		
					<h2>Edycja "${ID}"</h2>
					<p>${message}</p>
					<form:form action="admin_seances.html" method="POST" commandName="seanceForm">
						<table>
							<tr>
								<td>ID:</td>
								<td><form:input path="ID"/></td>
							</tr>
							<tr>
								<td>Czas trwania:</td>
								<td><form:input path="duration"/></td>
							</tr>
							<tr>
								<td>Numer sali:</td>
								<td><form:input path="roomNumber"/></td>
							</tr>
							<tr>
								<td>Tytuł filmu:</td>
								<td><form:input path="title"/></td>
							</tr>
							<tr>
								<td>Data:</td>
								<td><form:input path="date" id="datepicker"/></td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" name="action" value="Zapisz zmiany" /></td>
							</tr>
						</table>
					</form:form>
				
			</section>
		</div>
	</div>
</body>
</html>
