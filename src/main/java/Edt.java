
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import emploidutemps.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;

/**
 * Servlet implementation class Edt
 */
public class Edt extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final byte NB_JOUR_PLAGE = 7;//jours dans la semaine
	private static final byte MAX_NB_JOUR_PLAGE = 21;//jours dans la semaine
	private static final byte HEURE_DEBUT = 7;//ie: 7h00
	private static final byte NB_HEURE_PLAGE = 15;//heures par jour
	private static final byte MAX_NB_HEURE_PLAGE = 24;//heures par jour
	
	Stockage stockage;
	EvenementMap evenements;
	PrintWriter out;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Edt() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		
		stockage = new Stockage(getServletContext().getRealPath("/"));
		
		try 
		{
			evenements = stockage.charger();
		} 
		catch (IOException | JAXBException | SAXException e) 
		{
			e.printStackTrace();
		}
		
		if (evenements == null) 
		{
			LocalDateTime premierjour_semaine = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1).atTime(8, 0);
			LocalDateTime autrejourplustard = premierjour_semaine.plusDays(4).plusHours(6);

			Evenement cours1 = new Evenement("anglais", "L3 Info", "Amphi A", "Jhonny", null, premierjour_semaine,
					premierjour_semaine.plusHours(2));

			Evenement cours2 = new Evenement("plage", "L3 Info", "Salines", "Aquaman", null, autrejourplustard,
					autrejourplustard.plusHours(6));

			evenements = new EvenementMap();
			evenements.put(cours1);
			evenements.put(cours2);
		}
		
		this.getServletContext().setAttribute("cours", evenements);
	}

	
	@Override
	public void destroy() 
	{
		super.destroy();
		
		try 
		{
			stockage.sauvegarder(evenements);
		}
		catch (IOException | JAXBException | SAXException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		out = response.getWriter();
		PlageJours plage = getParams(request);
		
		if(plage.getDebut() == null)
			plage.setDebut(
					LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1)
					.atStartOfDay().plusHours(HEURE_DEBUT));
		
		if(plage.getFin() == null)
			plage.setDuree(Duration.of(NB_JOUR_PLAGE, ChronoUnit.DAYS));

		Object[][] squelette_table = genererSquelette(plage, null);
		
		
		request.setAttribute("squelette", squelette_table);
		request.setAttribute("plage", plage);
		
		RequestDispatcher rd = request.getRequestDispatcher("page.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private PlageJours getParams(HttpServletRequest request)
	{
		boolean precedante = false;
		boolean suivante = false;
		LocalDateTime debut = null;
		int jours = NB_JOUR_PLAGE;
		int heures = NB_HEURE_PLAGE;
		Object attr = null;
		
		precedante = (request.getParameter("prec") != null);
		suivante = (!precedante && request.getParameter("suiv") != null);
		
		attr = request.getParameter("jours");
		if(attr != null) 
		{
			out.println("jours:" +jours);///
			jours = Integer.parseInt((String)attr);
			if (jours > MAX_NB_JOUR_PLAGE) jours = NB_JOUR_PLAGE;
		}
		
		attr = request.getParameter("heures");
		if(attr != null)
		{
			heures = Integer.parseInt((String)attr);
			out.println("heures:"+heures);///
			if(heures > MAX_NB_HEURE_PLAGE) heures = NB_HEURE_PLAGE;
		}
		
		attr = request.getParameter("debut");
		if(attr != null) 
		{
			String date = (String)attr;
			out.println(date);///
			debut = LocalDateTime.parse(date);
			
			if(heures + debut.getHour() > 24) heures = 24 - debut.getHour();
			
			if(precedante)
				debut = debut.minusDays(jours);
			else if(suivante)
				debut = debut.plusDays(jours);
		}
		
		return new PlageJours(debut, jours, heures);
	}
	
	//TODO: Adapter à un affichage par quart d'heures 
	private Object[][] genererSquelette(PlageJours plage, ParamsRecherche paramsFiltres)
	{	
		LocalDateTime debut = plage.getDebut();
		int dureeJours = (int)plage.getDuree().toDays();
		int heuresJourMax = plage.getHeuresJour();
		
		if(dureeJours < 1) return null;
		
		Object[][] squelette_table = new Object[dureeJours][heuresJourMax];
		
		for (int k : evenements.keySet()) {

			Evenement sceance = evenements.get(k);
			
			Plage intersection = plage.getIntersection(sceance);

			//pas inclu dans la plage
			if (intersection == null) continue;
			
			//TODO: ajouter à l'interface l'option de filtration
			if(paramsFiltres != null && !sceance.match(paramsFiltres)) continue;

			byte jour = (byte)ChronoUnit.DAYS.between(debut, intersection.getDebut());
			byte heure = (byte)ChronoUnit.HOURS.between(debut.plusDays(jour), intersection.getDebut());				
			
			for (int compte = 0; compte < intersection.getDureeHeures(); compte++)
			{				
				if (heure < heuresJourMax) 
				{
					squelette_table[jour][heure] = k;
					heure++;
				}
				else
				{// duree superieure à l'affichage: peut arriver sur le jour suivant
					compte += 24 - (heure + 1);
					heure = 0;
					
					jour++;
					if (jour > dureeJours - 1)
						break;
				}
			}
		}
		
		return squelette_table;
	}
}
