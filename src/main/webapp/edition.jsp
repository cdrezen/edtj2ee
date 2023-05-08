<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edition</title>
</head>
<body>
	<form id="ajoutrecherche_form">
	    <input type="hidden" id="_id" name ="id" value="${sceance.id}" >
		<input type="text" id="titre_txt" name="titre" minlength="3" value="${sceance.titre}" required>
		<input type="text" id="promo_txt" name="promo" value="${sceance.promotion}">
		<input type="text" id="salle_txt" name="salle" value="${sceance.salle}">
		<input type="text" id="prof_txt" name="prof" value="${sceance.professeur}">
		<input type="text" id="desc_txt" name="desc" value="${sceance.description}">
		<input type="datetime-local" id="date_debut" name="debut" value="${sceance.debut}">
		<input type="time" id="duree" name="duree" value="${duree}">
		<input type="submit" value="Modifier" formaction="edition" name="edit" formmethod="POST" />
		<input type="submit" value="Supprimer" formaction="edition" name="suppr" formmethod="POST" />
		<button onclick="history.back()">Annuler</button>
	</form>
</body>
</html>