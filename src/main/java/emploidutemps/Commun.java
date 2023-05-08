package emploidutemps;

import java.util.ArrayList;

import emploidutemps.Evenement;

public class Commun 
{
	public Evenement trouverDepuisId(int id, ArrayList<Evenement> cours)
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

	public int trouverIndexDepuisId(int id, ArrayList<Evenement> cours)
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
