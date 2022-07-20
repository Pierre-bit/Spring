package fr.orsys.fx.calendrier_gif.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.orsys.fx.calendrier_gif.business.Jour;
import fr.orsys.fx.calendrier_gif.dao.JourDao;
import fr.orsys.fx.calendrier_gif.exceptions.JourExistantException;
import fr.orsys.fx.calendrier_gif.service.JourService;
import lombok.AllArgsConstructor;

@Service
// Le constructeur avec tous les parametre va provoquer l'injection de dépendances par Spring
//Spring va d'abord instancier JourDao avant jourService
@AllArgsConstructor
public class JourServiceImpl implements JourService {

	// On définit les dépendances de ce service
	private JourDao jourDao;

	@Override
	public List<Jour> recupererJours() {
		return jourDao.findAll();
	}

	@Override
	public Jour recupererJour(LocalDate localDate) {
		return jourDao.findById(localDate).orElse(null);
	}

	@Override
	public Page<Jour> recupererJours(Pageable pageable) {
		return jourDao.findAll(pageable);
	}

	@Override
	public LocalDate LastDayOFBase() {
		return jourDao.FindLastDayToMonth();
	}

	@Override
	public Jour ajouterJour(LocalDate date) throws JourExistantException {
		if (jourDao.existsById(date)) {
			throw new JourExistantException("Ce jour est déjà présent en base");
		}

		return jourDao.save(new Jour(date));
	}

	@Override
	public boolean jourDelete(LocalDate date) {
		Jour jour = recupererJour(date);
		if (jour == null) {
			return false;
		} else {
			jourDao.delete(jour);
			return true;
		}
	}
	
	
	@Override
	public Jour updateJour(LocalDate date,int nbPoints) {
		Jour jour = recupererJour(date);

		jour.setNbPoints(nbPoints);
		return jourDao.save(jour);
	}

	@Override
	public Jour ajouterJourSuivant(Jour jourSuivant) {
		return jourDao.save(jourSuivant);
	}

	@Override
	public Jour enregistrerJour(Jour jour) {
		return jourDao.save(jour);
	}

	@Override
	public Jour mettreAJour(LocalDate date, int nouveauNbPoints) {
		Jour jour = recupererJour(date);

		jour.setNbPoints(nouveauNbPoints);
		return jourDao.save(jour);
	}

}
