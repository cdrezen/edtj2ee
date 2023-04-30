<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<%
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
		%>
	</table>
</body>
</html>