package fr.orsys.fx.calendrier_gif.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.orsys.fx.calendrier_gif.business.Jour;
import fr.orsys.fx.calendrier_gif.exceptions.JourExistantException;

public interface JourService {

	/**
	 * Cette méthode renvoie la liste exhaustive des jours stockés dans la table
	 * jour
	 * 
	 * @return
	 */
	List<Jour> recupererJours();

	Jour recupererJour(LocalDate localDate);

	/**
	 * Cette méthode renvoie une page de jours
	 * 
	 * @param pageable (object qui correspond à une demande de page)
	 * @return une page de jours
	 */
	Page<Jour> recupererJours(Pageable pageable);
	
	LocalDate LastDayOFBase();
	
    Jour ajouterJour(LocalDate date) throws JourExistantException ;
    
    boolean jourDelete(LocalDate date);

	Jour updateJour(LocalDate date, int nbPoints);

	Jour ajouterJourSuivant(Jour jourSuivant);

	Jour enregistrerJour(Jour jour);

	Jour mettreAJour(LocalDate date, int nouveauNbPoints);
    
}