<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="emploidutemps.Evenement"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="styles.css">
<title>Insert title here</title>
</head>
<body>
	<h3 align="center">Resultats de la recherche "<small style="color:grey;"><i>${param}</i></small>"</h3>
	<table>
		<tr>
			<th>Titre</th>
			<th>Début</th>
			<th>Fin</th>
			<th>Promotion</th>
			<th>Salle</th>
			<th>Professeur</th>
			<th>Remarque</th>
			<th>Edition</th>
		</tr>
		<c:forEach items="${resultats.keySet()}" var="k">
		<c:set var="sceance" value="${resultats[k]}" />
		<tr>
			<th>${sceance.titre}</th>
			<fmt:parseDate value="${sceance.debut}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
			<th><fmt:formatDate type = "both" value = "${parsedDate}" /></th>
			<fmt:parseDate value="${sceance.fin}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
			<th><fmt:formatDate type = "both" value = "${parsedDate}" /></th>
			<th>${sceance.promotion}</th>
			<th>${sceance.salle}</th>
			<th>${sceance.professeur}</th>
			<th>${sceance.description}</th>
			<th>
			<form id="edition_form" action="edition">
			<input type="hidden" id="_id" name ="id" value="${k}" >
			<input type="submit" value="Supprimer" name="suppr" formmethod="POST" />
			<input type="submit" value="Modifier" formaction="edition" formmethod="GET" />
	        </form>
			</th>
		</tr>
	    </c:forEach>
	</table>
</body>
</html>