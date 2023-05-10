

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
		
		int id;
		
		Object attr = request.getParameter("id");
		if(attr != null) id = Integer.parseInt((String)attr);
		else return;
		
		ArrayList<Evenement> cours = getEvenements();		
		Evenement sceance = Evenement.trouverDepuisId(id, cours);
		
		if(sceance == null) return;
		
		request.setAttribute("sceance", sceance);
		Duration duree = Duration.between(sceance.getDebut(), sceance.getFin());
		request.setAttribute("duree", LocalTime.of(duree.toHoursPart(), duree.toMinutesPart()));
		RequestDispatcher rd = request.getRequestDispatcher("edition.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		enum modif { AJOUTER, MODIFIER, SUPPRIMER} ;
		
		modif type_modif = null;
		RequestDispatcher rd;
		
		out = response.getWriter();
		
		///dbg
		
		Enumeration<String> params = request.getParameterNames(); 
		while(params.hasMoreElements()){
		 String paramName = params.nextElement();
		 out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		}
		///
		
		if(request.getParameterMap().containsKey("suppr")) type_modif = modif.SUPPRIMER;
		else if(request.getParameterMap().containsKey("ajout")) type_modif = modif.AJOUTER;
		else if(request.getParameterMap().containsKey("edit")) type_modif = modif.MODIFIER;
		
		if(type_modif == null) return;

		Evenement nouveau_cours = null;
		int id = -1;
		int index = -1;
		
		if(type_modif == modif.AJOUTER || type_modif == modif.MODIFIER) 
		{
			nouveau_cours = getParamsModif(request, type_modif == modif.AJOUTER);
			
			if(nouveau_cours == null)
			{
				out.println("Parametres incorrectes.");
				return;
			}
			
			id = nouveau_cours.getId();
		}
		else // cas SUPPRIMER
		{
			Object attr = request.getParameter("id");
			
			if(attr == null) 
			{
				out.println("Parametres incorrectes.");
				return;
			}
			
			id = Integer.parseInt((String)attr);
		}
		
		ArrayList<Evenement> cours = getEvenements();	
		
		if(type_modif == modif.SUPPRIMER || type_modif == modif.MODIFIER) 
		{
			index = Evenement.trouverIndexDepuisId(id, cours);
			if(index == -1)
			{
				out.println("Impossible de trouver l'évenement à modifier.");
				return;
			}
		}
		
		switch(type_modif) 
		{
		
		case SUPPRIMER:
		{
			cours.remove(index);
			break;
		}
		case AJOUTER:
		{
			cours.add(nouveau_cours);
			break;
		}
			
		case MODIFIER:
		{
			cours.set(index, nouveau_cours);
			break;
		}
			
		default: 
			break;
		
		}
		
		getServletContext().setAttribute("cours", cours);
		response.sendRedirect("edt");
	}
	
	private ArrayList<Evenement> getEvenements()
	{
		Object attr = getServletContext().getAttribute("cours");
		
	    if(attr == null) throw new RuntimeException("Echec de la récupéreration des données dans l'attribut.");
	    return (ArrayList<Evenement>)attr;
	}
	
	private Evenement getParamsModif(HttpServletRequest request, boolean ajout)
	{
		int id = -1;
		String titre = null;
		String promotion = null;
		String salle = null;
		String professeur = null;
		String description = null;
		LocalDateTime debut = null;
		LocalDateTime fin = null;
		Object attr = null;
		
		if(!ajout) 
		{
			attr = request.getParameter("id");
			if(attr != null) id = Integer.parseInt((String)attr);
		}
		
		attr = request.getParameter("titre");
		if(attr == null) return null;
		titre = (String)attr;
		
		attr = request.getParameter("promo");
		if(attr != null) promotion = (String)attr;
		
		attr = request.getParameter("salle");
		if(attr != null) salle = (String)attr;
		
		attr = request.getParameter("prof");
		if(attr != null) professeur = (String)attr;
		
		attr = request.getParameter("desc");
		if(attr != null) description = (String)attr;
			
		attr = request.getParameter("debut");
		if(attr == null) return null;
		String date = (String)attr;
		debut = LocalDateTime.parse(date);
		
		attr = request.getParameter("duree");
		if(attr == null) return null;
		String temps = (String)attr;
		LocalTime duree = LocalTime.parse(temps);
		fin = debut.plusHours(duree.getHour());
		fin = fin.plusMinutes(duree.getMinute());
		
		Evenement sceance = null; 
		if(!ajout) sceance = new Evenement(id, titre, promotion, salle, professeur, description, debut, fin);
		else sceance = new Evenement(titre, promotion, salle, professeur, description, debut, fin);//creation auto d'id
		
		return sceance;
	}

}
