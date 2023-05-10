
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	
	ArrayList<Evenement> cours;
	
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
		
		LocalDateTime premierjour_semaine = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1).atTime(8, 0);
		LocalDateTime autrejourplustard = premierjour_semaine.plusDays(2).plusHours(5);
		
		Evenement cours1 = new Evenement("anglais", premierjour_semaine,
				premierjour_semaine.plusHours(2));
		
		Evenement cours2 = new Evenement("plage", autrejourplustard,
				autrejourplustard.plusHours(1));
		
		cours = new ArrayList<Evenement>(List.of(cours1, cours2));
		
		this.getServletContext().setAttribute("cours", cours);
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
		request.setAttribute("plage_horraire", new LocalDateTime[] {plage.getDebut(), plage.getFin()});
		request.setAttribute("nb_jour", plage.getDuree().toDays());////
		request.setAttribute("nb_heure", plage.getHeuresJour());
		request.setAttribute("heure_debut", plage.getDebut().getHour());
		
		RequestDispatcher rd = request.getRequestDispatcher("page.jsp");
		rd.forward(request, response);
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
			jours = Integer.parseInt((String)attr);
			if (jours > MAX_NB_JOUR_PLAGE) jours = NB_JOUR_PLAGE;
		}
		
		attr = request.getParameter("heures");
		if(attr != null)
		{
			heures = Integer.parseInt((String)attr);
			if(heures > MAX_NB_HEURE_PLAGE) heures = NB_HEURE_PLAGE;
		}
		
		attr = request.getParameter("debut");
		if(attr != null) 
		{
			String date = (String)attr;
			debut = LocalDateTime.parse(date);
			
			if(precedante)
				debut = debut.minusDays(jours);
			else if(suivante)
				debut = debut.plusDays(jours);
		}
		
		return new PlageJours(debut, jours, heures);
	}
	
	private Object[][] genererSquelette(PlageJours plage, Filtre[] filtres)
	{	
		LocalDateTime debut = plage.getDebut();
		LocalDateTime fin = plage.getFin();
		int dureeJours = (int)plage.getDuree().toDays();
		int heuresJour = plage.getHeuresJour();
		
		if(dureeJours < 1) return null;
		
		Object[][] squelette_table = new Object[dureeJours][heuresJour];
		
		for (int i = 0; i < cours.size(); i++) {

			Evenement sceance = cours.get(i);

			if (!sceance.estIncluDans(debut, fin))
				continue;
			
			if(filtres != null) 
			{
				boolean estRetenu = false;
				
				for(Filtre filtre : filtres)
				{
					if(filtre.valide(sceance)) 
					{
						estRetenu = true;
						break;
					}
				}
				
				if(!estRetenu) continue;
			}

			byte jour = (byte) (sceance.getDebut().getDayOfWeek().getValue() - 1);
			byte heure = (byte) (sceance.getDebut().getHour() - debut.getHour());
			out.println(jour + " " + heure + "=" + sceance.getDebut().getHour() +"-" + debut.getHour());//
			//TODO: regeler pb heure superieur : peut etre du a heureJour mal init dans PlageJours
			//retour de edition

			for (int minutes = 0; minutes < sceance.getDureeMinutes(); minutes += 60) {
				
				squelette_table[jour][heure] = (byte) i;
				heure++;
				if (heure > heuresJour - 1) {
					heure = 0;
					jour++;
					if (jour > dureeJours)
						break;
				}
			}
		}
		
		return squelette_table;
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

}
