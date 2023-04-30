<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- <%@ page import="emploidutemps.Evenement"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.temporal.ChronoField"%> --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
	Plage horraire : du ${plage_horraire[0]} au ${plage_horraire[1]}
	
	<table>
		<tr>
			<td>&nbsp;</td>
			<th>Lundi</th>
			<th>Mardi</th>
			<th>Mercredi</th>
			<th>Jeudi</th>
			<th>Vendredi</th>
			<th>Samedi</th>
			<th>Dimanche</th>
		</tr>
		
		<%--

		/*DateTimeFormatter formatheure = DateTimeFormatter.ofPattern("HH:mm");

		for (int iheure = 7; iheure <= 21; iheure++) {
			jourheure = jourheure.withHour(iheure);
			out.println("<tr><th>" + jourheure.format(formatheure) + "</th>");

			for (int ijour = 1; ijour <= 7; ijour++) {
				String contenu = "&nbsp;";

				Object index = squelette_table[ijour - 1][iheure - 7];

				if (index != null) {
			contenu = cours.get((byte) index).getTitre();
				}

				out.println("<td>" + contenu + "</td>");
				jourheure = jourheure.plusDays(1);
			}
			out.println("</tr>");
		} */
		--%>
		
		<c:forEach var="heure" begin="0" end="${nb_heure - 1}">		
			<tr>
				<th>${heure + heure_debut}h00</th>	
				<c:forEach var="jour" begin="0" end="${nb_jour - 1}">	
					<c:set var="index" value="${squelette[jour][heure]}" />
					<td>${cours[index].titre}</td>	
				</c:forEach>
			</tr>		
		</c:forEach>
	</table>

	<label for="start">Recherche:</label>
	<form action="recherche" method="GET">
		<input type="text" id="recherche_txt" name="recherche"> <input
			type="submit" value="Envoyer" />
	</form>
</body>
</html>