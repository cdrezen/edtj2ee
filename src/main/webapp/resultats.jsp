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
	<%
	Object attr = request.getAttribute("resultats");
	if (attr == null) throw new Exception("Echec de la récupéreration des données dans l'attribut.");

	ArrayList<Evenement> resultats = (ArrayList<Evenement>) attr;
	%>
	<h3>Resultats de la recherche "${param.recherche}"</h3>
	<table>
		<tr>
			<th>Titre</th>
			<th>Début</th>
			<th>Fin</th>
			<th>Promotion</th>
			<th>Salle</th>
			<th>Professeur</th>
			<th>Remarque</th>
		</tr>
		<c:forEach items="${cours}" var="sceance">
		<tr>
			<th>${sceance.titre}</th>
			<fmt:parseDate value="${sceance.debut}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
			<th><fmt:formatDate type = "both" value = "${parsedDate}" /></th>
			<fmt:parseDate value="${sceance.fin}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
			<th><fmt:formatDate type = "both" value = "${parsedDate}" /></th>
			<th></th>
			<th></th>
			<th></th>
			<th></th>
		</tr>
	    </c:forEach>
		<%-- <%
		DateTimeFormatter formatheure = DateTimeFormatter.ofPattern("'le' EEEE d MMMM uuuu '('dd/MM/uuuu G')' 'à' HH'h':mm ");
		
		for(Evenement sceance : resultats)
		{
			out.println("<tr>");
			out.print("<td>" + sceance.getTitre() + "</td>");
			out.print("<td>" + sceance.getDebut().format(formatheure) + "</td>");
			out.print("<td>" + sceance.getFin().format(formatheure) + "</td>");
			//TODO: ajouter les bons trucs une fois implementés
			out.print("<td>" + "&nbsp;" + "</td>");
			out.print("<td>" + "&nbsp;" + "</td>");
			out.print("<td>" + "&nbsp;" + "</td>");
			out.print("<td>" + "&nbsp;" + "</td>");
			out.println("</tr>");
		}
		%> --%>
	</table>
</body>
</html>