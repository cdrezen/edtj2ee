package emploidutemps;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
//import jakarta.annotation.Id;
//import jakarta.annotation.Generated;
import java.util.ArrayList;

public class Evenement extends emploidutemps.Plage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private static int newid = 0;

	//@Id
    //@Generated
	int id;
	String titre;
	String promotion;
	String salle;
	String professeur;
	String description;
	LocalDateTime debut;
	LocalDateTime fin;

	public Evenement() {
		super();
		this.id = newid++;
	}

	public Evenement(String titre, LocalDateTime debut, LocalDateTime fin) {
		super(debut, fin);
		this.id = newid++;
		this.titre = titre;
	}
	
	public Evenement(int id, String titre, LocalDateTime debut, LocalDateTime fin) {
		super(debut, fin);
		this.id = id;
		this.titre = titre;
	}

	public Evenement(String titre, String promotion, String salle, String professeur, String description,
			LocalDateTime debut, LocalDateTime fin) {
		super(debut, fin);
		this.id = newid++;
		this.titre = titre;
		this.promotion = promotion;
		this.salle = salle;
		this.professeur = professeur;
		this.description = description;
	}
	
	public Evenement(int id, String titre, String promotion, String salle, String professeur, String description,
			LocalDateTime debut, LocalDateTime fin) {
		super(debut, fin);
		this.id = id;
		this.titre = titre;
		this.promotion = promotion;
		this.salle = salle;
		this.professeur = professeur;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getSalle() {
		return salle;
	}

	public void setSalle(String salle) {
		this.salle = salle;
	}

	public String getProfesseur() {
		return professeur;
	}

	public void setProfesseur(String professeur) {
		this.professeur = professeur;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static Evenement trouverDepuisId(int id, ArrayList<Evenement> cours)
	{	
		for(Evenement sceance : cours)
		{
			int id_courante = sceance.getId();
			if(id_courante == id) {
				return sceance;
			}
		}
		
		return null;
	}

	public static int trouverIndexDepuisId(int id, ArrayList<Evenement> cours)
	{	
		for(int i = 0; i < cours.size(); i++)
		{
			int id_courante = cours.get(i).getId();
			if(id_courante == id) {
				return i;
			}
		}
		
		return -1;
	}
}
