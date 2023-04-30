package emploidutemps;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Evenement implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	String titre;
	String description;
	LocalDateTime debut;
	LocalDateTime fin;
	
	public Evenement() {
		super();
	}
	
	public Evenement(String titre, LocalDateTime debut, LocalDateTime fin) {
		super();
		this.titre = titre;
		this.debut = debut;
		this.fin = fin;
	}
	
	public Evenement(String titre, String description, LocalDateTime debut, LocalDateTime fin) {
		super();
		this.titre = titre;
		this.description = description;
		this.debut = debut;
		this.fin = fin;
	}
	
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
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
	
	public boolean estInclu(LocalDateTime date)
	{
		return !(date.isBefore(debut) || date.isAfter(fin));
	}
	
	public boolean aDebutIncluDans(LocalDateTime debutInterval, LocalDateTime finInterval)
	{
		return !(debut.isBefore(debutInterval) || debut.isAfter(finInterval));
	}
	
	public boolean aFinIncluDans(LocalDateTime debutInterval, LocalDateTime finInterval)
	{
		return !(fin.isBefore(debutInterval) || fin.isAfter(finInterval));
	}
	
	public boolean estIncluDans(LocalDateTime debutInterval, LocalDateTime finInterval)
	{
		return (aDebutIncluDans(debutInterval, finInterval) || aFinIncluDans(debutInterval, finInterval));
	}
	
	public long getDureeMinutes() {
		return debut.until(fin, ChronoUnit.MINUTES);
	}
	
	public long getDureeHeures() {
		return debut.until(fin, ChronoUnit.HOURS);
	}
}
