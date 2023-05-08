
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import emploidutemps.Evenement;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;

/**
 * Servlet implementation class Edt
 */
public class Edt extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final byte NB_JOUR_PLAGE = 7;//jours dans la semaine
	private static final byte HEURE_DEBUT = 7;//ie: 7h00
	private static final byte NB_HEURE_PLAGE = 15;//heures par jour
	
	ArrayList<Evenement> cours;

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
		
		//PrintWriter out = response.getWriter();
		
		Object[][] squelette_table = new Object[NB_JOUR_PLAGE][NB_HEURE_PLAGE];
		
		LocalDateTime premierjour_semaine = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1).atStartOfDay();
		LocalDateTime[] plage_horraire = { premierjour_semaine, premierjour_semaine.plusDays(6) };

		for (int i = 0; i < cours.size(); i++) {

			Evenement sceance = cours.get(i);

			if (!sceance.estIncluDans(plage_horraire[0], plage_horraire[1]))
				continue;

			byte jour = (byte) (sceance.getDebut().getDayOfWeek().getValue() - 1);
			byte heure = (byte) (sceance.getDebut().getHour() - HEURE_DEBUT);
			//out.println(jour + ":" + heure +" d:" + sceance.getDureeHeures() +'\n');//

			for (int minutes = 0; minutes < sceance.getDureeMinutes(); minutes += 60) {
				
				squelette_table[jour][heure] = (byte) i;
				heure++;
				if (heure > NB_HEURE_PLAGE - 1) {
					heure = 0;
					jour++;
					if (jour > NB_JOUR_PLAGE)
						break;
				}
			}
		}
		
		request.setAttribute("squelette", squelette_table);
		request.setAttribute("plage_horraire", plage_horraire);
		request.setAttribute("nb_jour", NB_JOUR_PLAGE);
		request.setAttribute("nb_heure", NB_HEURE_PLAGE);
		request.setAttribute("heure_debut", HEURE_DEBUT);
		
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

}
