

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import emploidutemps.Evenement;

/**
 * Servlet implementation class Recherche
 */
public class Recherche extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Recherche() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Object attr = request.getParameter("recherche");
		if(attr == null) throw new RuntimeException("Echec de la récupéreration du search string dans la requete.");
		String recherche = (String)attr;
		
		if(recherche.isBlank()) return;//TODO: ajouter d'autre conditions de refus
		
		attr = getServletContext().getAttribute("cours");
	    if(attr == null) throw new RuntimeException("Echec de la récupéreration des données dans l'attribut.");
	    ArrayList<Evenement> cours = (ArrayList<Evenement>)attr;
	    ArrayList<Evenement> resultats = new ArrayList<Evenement>();
	    
	    for(Evenement sceance : cours) 
	    {
	    	if(sceance.getTitre().contains(recherche)) resultats.add(sceance);
	    }
	    
	    RequestDispatcher rd = request.getRequestDispatcher("resultats.jsp"); 
	    request.setAttribute("resultats", resultats);  
	    rd.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
