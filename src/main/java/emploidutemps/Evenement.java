package emploidutemps;

import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
//import jakarta.annotation.Id;
//import jakarta.annotation.Generated;
import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="Evenement")
public class Evenement extends emploidutemps.Plage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	String titre;
	String promotion;
	String salle;
	String professeur;
	String description;

	public Evenement() {
		super();
		
	}
	
	public Evenement(String titre, LocalDateTime debut, LocalDateTime fin) {
		super(debut, fin);
		this.titre = titre;
	}
	
	public Evenement(String titre, LocalDateTime debut, Duration duree) {
		super(debut, duree);
		this.titre = titre;
	}

	public Evenement(String titre, String promotion, String salle, String professeur, String description,
			LocalDateTime debut, LocalDateTime fin) {
		this(titre, debut, fin);
		this.promotion = promotion;
		this.salle = salle;
		this.professeur = professeur;
		this.description = description;
	}

	public Evenement(String titre, String promotion, String salle, String professeur, String description,
			LocalDateTime debut, Duration duree) {
		this(titre, debut, duree);
		this.promotion = promotion;
		this.salle = salle;
		this.professeur = professeur;
		this.description = description;
	}

	@XmlElement(required = true)
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	@XmlElement(required = false)
	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	@XmlElement(required = false)
	public String getSalle() {
		return salle;
	}

	public void setSalle(String salle) {
		this.salle = salle;
	}

	@XmlElement(required = false)
	public String getProfesseur() {
		return professeur;
	}

	public void setProfesseur(String professeur) {
		this.professeur = professeur;
	}

	@XmlElement(required = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean match(ParamsRecherche params) 
	{
		Filtre[] filtres = params.getFiltres();
		LocalDateTime param_debut = params.getDebut();
		Duration param_duree = params.getDuree();
		
		if(param_debut != null && param_debut.isAfter(this.fin)) return false;
		if(param_duree != null && !param_duree.equals(this.duree)) return false;
		
		if (filtres != null && filtres.length > 0) 
		{
			for (Filtre filtre : filtres) 
			{
				if (filtre.valide(this)) 
				{
					return true;
				}
			}
		}
		else 
		{
			if(param_debut != null && param_debut.equals(this.debut)) return true;
			if(param_duree != null && param_duree.equals(this.duree)) return true;
		}
		
		
		return false;
	}
}
