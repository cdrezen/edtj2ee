<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="styles.css">
<title>Edition</title>
</head>
<body>
	<form id="edition_form" class="edit" action="edition">
	    <input type="hidden" id="_id" name ="id" value="${param.id}" >
		<h4>Modifier/Supprimer:</h4>
		<fieldset>
			<legend>Informations</legend>
			<p>
				<label for="titre">Titre</label> <input
					type="text" id="titre_txt" name="titre" placeholder="Chimie" value="${sceance.titre}">
			</p>
			<p>
				<label for="promo">Promotion</label> <input type="text"
					id="promo_txt" placeholder="L3 Info" name="promo" value="${sceance.promotion}">
			</p>
			<p>
				<label for="salle">Salle</label> <input type="text" id="salle_txt"
					placeholder="Amphi A" name="salle" value="${sceance.salle}">
			</p>
			<p>
				<label for="prof">Professeur</label> <input type="text"
					id="prof_txt" placeholder="Sabine" name="prof" value="${sceance.professeur}">
			</p>
			<p>
				<label for="desc">Description/Remarque</label>
				<textarea id="desc_txt" placeholder="c'est un cours de chimie" name="desc">${sceance.description}</textarea>
			</p>
		</fieldset>
		<fieldset>
		<legend>Horaires</legend>
			<p>
			<label for="debut">Début de l'evenement</label>
			<input type="datetime-local" id="date_debut" name="debut" value="${sceance.debut}">
			</p>
			<p>
			<label for="duree">Durée de l'evenement</label>
			<input type="time" id="duree" name="duree" value="${sceance.dureeLocalTime}">
			</p>
		</fieldset>
		<div style="text-align:right;margin:1em;">
		    <input type="submit" value="Modifier" name="edit" formmethod="POST" />
		    <input type="submit" value="Supprimer" name="suppr" formmethod="POST" />
		    <button onclick="history.back()">Annuler</button>
		</div>
	</form>
</body>
</html>