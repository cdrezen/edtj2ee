<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="emploidutemps.Evenement" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.ChronoField" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
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
      <%
      ArrayList<Evenement> cours = (ArrayList<Evenement>)request.getAttribute("cours");
      
      LocalDateTime premierjour_semaine = LocalDate.now().with(ChronoField.DAY_OF_WEEK , 1).atStartOfDay();
      LocalDateTime jourheure = premierjour_semaine;
      
      Object[][] squelette_table = new Object[7][15];
      
      for (int i = 0; i < cours.size(); i++)
	  {
    	  Evenement sceance = cours.get(i);
    	  if(!sceance.estIncluDans(premierjour_semaine, premierjour_semaine.plusDays(6))) continue;
    	  byte jour = (byte)(sceance.getDebut().getDayOfWeek().getValue() - 1);
    	  byte heure = (byte)(sceance.getDebut().getHour() - 7);
    	  //out.println(jour + ":" + heure +" d:" + sceance.getDureeHeures() +'\n');//
    	  
    	  for(int minutes = 0; minutes < sceance.getDureeMinutes(); minutes += 60)
    	  {
    		  squelette_table[jour][heure] = (byte)i;
    		  heure++;
    		  if(heure > 14)
    		  { 
    			  heure = 0; 
    			  jour++;
    			  if(jour > 6) break;
    		  }
    	  }
	  }
      
      out.println("premier jour semaine: " + jourheure.toString());//
      
      DateTimeFormatter formatheure = DateTimeFormatter.ofPattern("HH:mm");
      
      for(int iheure = 7; iheure <= 21; iheure++)
      {
    	  jourheure = jourheure.withHour(iheure);
    	  out.println("<tr><th>" + jourheure.format(formatheure) + "</th>");
    	  
    	  for(int ijour = 1; ijour <= 7; ijour++)
          {
    		  String contenu = "&nbsp;";
    		  
    		  Object index = squelette_table[ijour - 1][iheure - 7];
    		  
    		  if(index != null)
    		  {
    			  contenu = cours.get((byte)index).getTitre();
    		  }
    		  
    		  out.println("<td>" + contenu + "</td>");
    		  jourheure = jourheure.plusDays(1);
          }
    	  out.println("</tr>");
      }
      %>
</table>
</body>
</html>