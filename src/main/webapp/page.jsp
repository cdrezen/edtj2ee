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
	<form id="plage_form" class="plage" action="edt" method="GET">
	    <input type="submit" value="<" name="prec" />
	    <input type="datetime-local" id="debut_plage" name="debut" value="${plage_horraire[0]}" onchange="this.form.submit()">
	    au
	    <input type="datetime-local" id="fin_plage" name="fin" value="${plage_horraire[1]}" disabled>
	    <input type="submit" value=">" name="suiv" />
	    <br>sur
	    <input type="number" name="jours" value="7" style="width:3em;" onchange="this.form.submit()"/>
	    jours de
	    <input type="number" name="heures" value="15" style="width:3em;" onchange="this.form.submit()"/>
	    heures.
	</form>
	<table id="edt_table">
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
	<form id="edition_form" class="edit">
		<h4>Rechercher/Modifier:</h4>
		<fieldset>
			<legend>Informations</legend>
			<p>
				<label for="titre">Titre<abbr title="required">*</abbr></label> <input
					type="text" id="titre_txt" name="titre" placeholder="Chimie"
					minlength="3" required>
			</p>
			<p>
				<label for="promo">Promotion</label> <input type="text"
					id="promo_txt" placeholder="L3 Info" name="promo">
			</p>
			<p>
				<label for="salle">Salle</label> <input type="text" id="salle_txt"
					placeholder="Amphi A" name="salle">
			</p>
			<p>
				<label for="prof">Professeur</label> <input type="text"
					id="prof_txt" placeholder="Sabine" name="prof">
			</p>
			<p>
				<label for="desc">Description/Remarque</label>
				<textarea id="desc_txt" placeholder="c'est un cours de chimie" name="desc"></textarea>
			</p>
		</fieldset>
		<fieldset>
		<legend>Horaires</legend>
			<p>
			<label for="debut">Début de l'evenement</label>
			<input type="datetime-local" id="date_debut" name="debut" value="${plage_horraire[0]}">
			</p>
			<p>
			<label for="duree">Durée de l'evenement</label>
			<input type="time" id="duree" name="duree" value="01:00">
			</p>
		</fieldset>
		<div style="text-align:right;margin:1em;">
			<input type="submit" value="Ajouter" formaction="edition" name="ajout" formmethod="POST" />
			<input type="submit" value="Rechercher" formaction="recherche" formmethod="GET" />
		</div>
	</form>
</body>
</html>