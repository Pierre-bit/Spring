package fr.orsys.fx.calendrier_gif.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.orsys.fx.calendrier_gif.business.Utilisateur;
import fr.orsys.fx.calendrier_gif.dao.UtilisateurDao;
import fr.orsys.fx.calendrier_gif.exceptions.UtilisateurExistantException;
import fr.orsys.fx.calendrier_gif.service.UtilisateurService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurDao utilisateurDao;

	@Override
	public List<Utilisateur> recupererUtilisateurs() {
		return utilisateurDao.findAll();
	}

	@Override
	public Utilisateur recupererUtilisateur(String email, String motDePasse) {
//		return utilisateurDao.findUserWithMailAndPwd(email, motDePasse);
		return utilisateurDao.findByEmailAndMotDePasse(email, motDePasse);
	}

	@Override
	public Page<Utilisateur> recupererUtilisateurs(Pageable pageable) {
		return utilisateurDao.findAll(pageable);
	}

	@Override
	public Utilisateur enregistrerUtilisateur(@Valid Utilisateur utilisateur) {
		if (utilisateurDao.findByEmail(utilisateur.getEmail())!=null) {
			throw new UtilisateurExistantException("L'email est déjà présent en base");
		}
		return utilisateurDao.save(utilisateur);
	}

	@Override
	@Transactional
	public boolean supprimerUser(Long id) {
		Utilisateur user = recupererUser(id);
		if (user == null) {
			return false;
		} else {
			utilisateurDao.delete(user);
			return true;
		}

	}

	@Override
	public Utilisateur recupererUser(Long id) {
		return utilisateurDao.findById(id).orElse(null);
	}

	@Override
	public Utilisateur updateUser(Long id, String nom, String prenom, String email, String motDePasse, int nbPoints) {
		Utilisateur user = recupererUser(id);
		user.setNom(nom);
		user.setPrenom(prenom);
		user.setEmail(email);
		user.setMotDePasse(motDePasse);
		user.setNbPoints(nbPoints);
		return utilisateurDao.save(user);
	}

	@Override
	public List<Utilisateur> recupererUtilisateurs(String filter) {
		
		return utilisateurDao.findAll();
	}

	@Override
	public Page<Utilisateur> recupererUtilisateurs(Pageable pageable, String filter) {

		return utilisateurDao.findByNomStartingWithIgnoreCase(pageable,filter);
	}



	
	
	
	

}
