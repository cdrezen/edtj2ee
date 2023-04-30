

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import emploidutemps.Evenement;
import jakarta.servlet.RequestDispatcher;


/**
 * Servlet implementation class Edt
 */
public class Edt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Evenement cours1 = new Evenement("anglais", LocalDateTime.parse("2023-04-29T08:00"), LocalDateTime.parse("2023-04-29T10:00"));
		Evenement cours2 = new Evenement("plage", LocalDateTime.parse("2023-04-25T10:00"), LocalDateTime.parse("2023-04-25T12:00"));
		ArrayList<Evenement> cours = new ArrayList<Evenement>(List.of(cours1, cours2));
		request.setAttribute("cours", cours);
		RequestDispatcher rd = request.getRequestDispatcher("page.jsp");
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
