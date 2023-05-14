<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
	    <input type="datetime-local" id="debut_plage" name="debut" value="${plage.debut}" onchange="this.form.submit()">
	    au
	    <input type="datetime-local" id="fin_plage" name="fin" value="${plage.fin}" disabled>
	    <input type="submit" value=">" name="suiv" />
	    <br>sur
	    <input type="number" name="jours" value="${param.jours != null ? param.jours : 7}" min="1" max="21" style="width:3em;" onchange="this.form.submit()"/>
	    jours de
	    <input type="number" name="heures" value="${param.heures != null ? param.heures : 15}" min="1" max="24" style="width:3em;" onchange="this.form.submit()"/>
	    heures.
	</form>
	<table id="edt_table">
		<tr>
			<td>&nbsp;</td>
			<c:set var="jours" value="#{['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche']}"/>
			<c:forEach var="i" begin="${plage.premierJourIndex - 1}" end="${plage.dureeJours - 1 + plage.premierJourIndex - 1}">
			    <th>${jours[i % 7]}</th>
			</c:forEach>
		</tr>

		<c:forEach var="heure" begin="0" end="${plage.heuresJour - 1}">
			<tr>
				<th>${heure + plage.heureDebut}h00</th>
				<c:forEach var="jour" begin="0" end="${plage.dureeJours - 1}">
					<c:set var="cle" value="${squelette[jour][heure]}" />
					<td>
					<b>${cours[cle].titre}</b>
					<br><i>${cours[cle].salle}</i>
					<br>${cours[cle].professeur}
					</td>
				</c:forEach>
			</tr>
		</c:forEach>

	</table>
	<form id="edition_form" class="edit">
		<h4>Rechercher/Modifier:</h4>
		<fieldset>
			<legend>Informations</legend>
			<p>
				<label for="titre">Titre</label> <input
					type="text" id="titre_txt" name="titre" placeholder="Chimie">
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
			<input type="datetime-local" id="date_debut" name="debut" value="${plage.debut}">
			<input type="checkbox" id="ch_debut" name="rdebut">
			</p>
			<p>
			<label for="duree">Durée de l'evenement</label>
			<input type="time" id="duree" name="duree" value="01:00">
			<input type="checkbox" id="ch_duree" name="rduree">
			</p>
		</fieldset>
		<div style="text-align:right;margin:1em;">
			<input type="submit" value="Ajouter" formaction="edition" name="ajout" formmethod="POST" />
			<input type="submit" value="Rechercher" formaction="recherche" formmethod="GET" />
		</div>
	</form>
</body>
</html>