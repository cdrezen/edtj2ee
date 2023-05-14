

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.time.Duration;

import emploidutemps.Evenement;
import emploidutemps.EvenementMap;

/**
 * Servlet implementation class Edition
 */
public class Edition extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;//
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edition() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//if(!request.getParameterMap().containsKey("id")) return;
		
		int id = -1;
		
		Object attr = request.getParameter("id");
		if(attr != null) id = Integer.parseInt((String)attr);
		else return;
				
		Evenement sceance = getEvenements().get(id);
		
		if(sceance == null) return;
		
		request.setAttribute("sceance", sceance);
		RequestDispatcher rd = request.getRequestDispatcher("edition.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		enum modif { AJOUTER, MODIFIER, SUPPRIMER} ;
		
		modif type_modif = null;
		boolean estModifie = false;
		int id = -1;
		
		out = response.getWriter();
		
		if(request.getParameterMap().containsKey("suppr")) type_modif = modif.SUPPRIMER;
		else if(request.getParameterMap().containsKey("ajout")) type_modif = modif.AJOUTER;
		else if(request.getParameterMap().containsKey("edit")) type_modif = modif.MODIFIER;
		
		if(type_modif == null) return;
		
		if(type_modif != modif.AJOUTER) // cas SUPPRIMER
		{
			Object attr = request.getParameter("id");
			
			if(attr == null) 
			{
				out.println("Parametres incorrectes.");
				return;
			}
			
			id = Integer.parseInt((String)attr);
		}
		
		EvenementMap cours = getEvenements();	
		
		switch(type_modif) 
		{
		
		case SUPPRIMER:
		{
			if(id != -1 && cours.remove(id) != null) 
				estModifie = true;
			break;
		}
		case AJOUTER:
		{
			if(cours.computeIfAbsent(k -> getParamsModif(request, null)) != null)
				estModifie = true;
			break;
		}
			
		case MODIFIER:
		{
			if(id != -1
					&& cours.computeIfPresent(id, 
						(k, v) -> getParamsModif(request, v)) != null)
				estModifie = true;
			break;
		}
			
		default: 
			break;
		
		}
		
		if(estModifie) getServletContext().setAttribute("cours", cours);
		response.sendRedirect("edt");
	}
	
	private EvenementMap getEvenements()
	{
		Object attr = getServletContext().getAttribute("cours");
		
	    if(attr == null) throw new RuntimeException("Echec de la récupéreration des données dans l'attribut.");
	    return (EvenementMap)attr;
	}
	
	private Evenement getParamsModif(HttpServletRequest request, Evenement prev)
	{
		LocalDateTime debut = null;
		LocalDateTime fin = null;
		
		String txt = (String)request.getParameter("debut");
		if(txt.isBlank()) 
		{
			if (prev != null) debut = prev.getDebut();
			else return null;
		}
		else debut = LocalDateTime.parse(txt);
		
		txt = (String)request.getParameter("duree");
		if(txt.isBlank()) 
		{
			if (prev != null) fin = prev.getFin();
			else return null;
		}
		else
		{
			LocalTime tduree = LocalTime.parse(txt);
			fin = debut.plusHours(tduree.getHour());
			fin = fin.plusMinutes(tduree.getMinute());
		};
		
		String titre = (String)request.getParameter("titre");
		String promotion = (String)request.getParameter("promo");
		String salle = (String)request.getParameter("salle");
		String professeur = (String)request.getParameter("prof");
		String description = (String)request.getParameter("desc");
		
		return new Evenement(titre, promotion, salle, professeur, description, debut, fin);
	}

}
