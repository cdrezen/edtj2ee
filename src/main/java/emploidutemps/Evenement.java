package emploidutemps;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
//import jakarta.annotation.Id;
//import jakarta.annotation.Generated;

public class Evenement implements java.io.Serializable {
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
		super();
		this.id = newid++;
		this.titre = titre;
		this.debut = debut;
		this.fin = fin;
	}
	
	public Evenement(int id, String titre, LocalDateTime debut, LocalDateTime fin) {
		super();
		this.id = id;
		this.titre = titre;
		this.debut = debut;
		this.fin = fin;
	}

	public Evenement(String titre, String promotion, String salle, String professeur, String description,
			LocalDateTime debut, LocalDateTime fin) {
		super();
		this.id = newid++;
		this.titre = titre;
		this.promotion = promotion;
		this.salle = salle;
		this.professeur = professeur;
		this.description = description;
		this.debut = debut;
		this.fin = fin;
	}
	
	public Evenement(int id, String titre, String promotion, String salle, String professeur, String description,
			LocalDateTime debut, LocalDateTime fin) {
		super();
		this.id = id;
		this.titre = titre;
		this.promotion = promotion;
		this.salle = salle;
		this.professeur = professeur;
		this.description = description;
		this.debut = debut;
		this.fin = fin;
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

	public LocalDateTime getDebut() {
		return debut;
	}

	public void setDebut(LocalDateTime debut) {
		this.debut = debut;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

	public boolean estInclu(LocalDateTime date) {
		return !(date.isBefore(debut) || date.isAfter(fin));
	}

	public boolean aDebutIncluDans(LocalDateTime debutInterval, LocalDateTime finInterval) {
		return !(debut.isBefore(debutInterval) || debut.isAfter(finInterval));
	}

	public boolean aFinIncluDans(LocalDateTime debutInterval, LocalDateTime finInterval) {
		return !(fin.isBefore(debutInterval) || fin.isAfter(finInterval));
	}

	public boolean estIncluDans(LocalDateTime debutInterval, LocalDateTime finInterval) {
		return (aDebutIncluDans(debutInterval, finInterval) || aFinIncluDans(debutInterval, finInterval));
	}

	public long getDureeMinutes() {
		return debut.until(fin, ChronoUnit.MINUTES);
	}

	public long getDureeHeures() {
		return debut.until(fin, ChronoUnit.HOURS);
	}
}
