

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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		enum modif { AJOUTER, MODIFIER, SUPPRIMER} ;
		
		int id = -1;
		modif type_modif = null;
		RequestDispatcher rd;
		String nextpage = "edt";
		
		out = response.getWriter();
		
		Enumeration<String> params = request.getParameterNames(); 
		while(params.hasMoreElements()){
		 String paramName = params.nextElement();
		 out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		}
		
		if(request.getParameterMap().containsKey("suppr")) type_modif = modif.SUPPRIMER;
		if(request.getParameterMap().containsKey("ajout")) type_modif = modif.AJOUTER;
		if(request.getParameterMap().containsKey("edit")) type_modif = modif.MODIFIER;
		
		switch(type_modif) 
		{
		case SUPPRIMER:
		{
			Object attr = request.getParameter("id");
			if(attr != null) 
			{
				id = Integer.parseInt((String)attr);
				ArrayList<Evenement> cours = getEvenements();
				
				for(int i = 0; i < cours.size(); i++) 
				{
					if(cours.get(i).getId() == id)
					{
						cours.remove(i);
						break;
					}
				}
				
				getServletContext().setAttribute("cours", cours);
			}	
			//else nextpage = "edition."
			break;
		}
		case AJOUTER:
		{
			Evenement nouveau_cours = getParamsModif(request, true);
			if(nouveau_cours == null) break;
			
			ArrayList<Evenement> cours = getEvenements();
			cours.add(nouveau_cours);
			getServletContext().setAttribute("cours", cours);
			break;
		}
			
		case MODIFIER:
		{
			Evenement nouveau_cours = getParamsModif(request, true);
			if(nouveau_cours == null) break;
			
			ArrayList<Evenement> cours = getEvenements();
			
			for(int i = 0; i < cours.size(); i++) 
			{
				if(cours.get(i).getId() == nouveau_cours.getId())
				{
					cours.set(i, nouveau_cours);
					break;
				}
			}
			
			getServletContext().setAttribute("cours", cours);
			break;
		}
			
		default: 
			//doGet(request, response);
			break;
		
		}
		
		rd = request.getRequestDispatcher("edt");
		rd.forward(request, response);
		
		doGet(request, response);
	}
	
	private ArrayList<Evenement> getEvenements()
	{
		Object attr = getServletContext().getAttribute("cours");
		
		///dbg
		Enumeration<String> params =getServletContext().getAttributeNames();
		while(params.hasMoreElements()){
			 String paramName = params.nextElement();
			 out.println("Attribut Name - "+paramName);
			}
		//
		
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
			if(attr != null) id = (int)attr;
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
