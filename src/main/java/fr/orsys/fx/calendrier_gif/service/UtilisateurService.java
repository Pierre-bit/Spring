package fr.orsys.fx.calendrier_gif.service;

import java.util.List;

import javax.servlet.Filter;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.orsys.fx.calendrier_gif.business.Utilisateur;

public interface UtilisateurService {

	/**
	 * 
	 * @return une liste d'utilsateurs
	 */

	List<Utilisateur> recupererUtilisateurs();

	/**
	 * 
	 * @param email
	 * @param motDePasse
	 * @return
	 */
	Utilisateur recupererUtilisateur(String email, String motDePasse);

	/**
	 * Cette méthode renvoie une page d'Utilisateurs
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Utilisateur> recupererUtilisateurs(Pageable pageable );

	Utilisateur enregistrerUtilisateur(@Valid Utilisateur utilisateur);
	
	boolean supprimerUser(Long id);
	
	Utilisateur recupererUser(Long id);
	
	Utilisateur updateUser(Long id,String nom,String prenom, String email,String motDePasse,int nbPoints);

	List<Utilisateur> recupererUtilisateurs(String filter);

	Page<Utilisateur> recupererUtilisateurs(Pageable pageable, String filter);


	
	
	
	
	
	

}
