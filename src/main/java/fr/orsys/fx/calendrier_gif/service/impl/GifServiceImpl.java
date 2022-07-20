package fr.orsys.fx.calendrier_gif.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.orsys.fx.calendrier_gif.business.Gif;
import fr.orsys.fx.calendrier_gif.business.GifDistant;
import fr.orsys.fx.calendrier_gif.business.GifTeleverse;
import fr.orsys.fx.calendrier_gif.business.Jour;
import fr.orsys.fx.calendrier_gif.business.Utilisateur;
import fr.orsys.fx.calendrier_gif.dao.GifDao;
import fr.orsys.fx.calendrier_gif.dao.GifDistantDao;
import fr.orsys.fx.calendrier_gif.dao.GifTeleverseDao;
import fr.orsys.fx.calendrier_gif.dao.JourDao;
import fr.orsys.fx.calendrier_gif.service.GifService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GifServiceImpl implements GifService {

	private GifDistantDao gifDistantDao;
	private GifDao gifDao;
	private GifTeleverseDao gifTeleverseDao;
	private JourDao jourDao;

	@Override
	public GifDistant ajouterGifDistant(GifDistant gifDistant, Utilisateur utilisateur) {
		gifDistant.setUtilisateur(utilisateur);
		gifDistant.setDateHeureAjout(LocalDate.now());
		return gifDistantDao.save(gifDistant);
	}

	@Override
	public GifTeleverse ajouterGifTeleverse(LocalDate localDate, Utilisateur utilisateur, String legende,
			String nomFichierOriginal, Jour jour) {
		GifTeleverse gifTeleverse = new GifTeleverse();
		gifTeleverse.setJour(jour);
		gifTeleverse.setDateHeureAjout(localDate);
		gifTeleverse.setUtilisateur(utilisateur);
		gifTeleverse.setLegende(legende);
		gifTeleverse.setNomFichierOriginal(nomFichierOriginal);
		return gifDao.save(gifTeleverse);
	}

	@Override
	public Gif recupererGifById(long id) {
		return gifDao.findById(id).orElse(null);
	}

//	@Override
//	public GifTeleverse ajouterGifTeleverse(LocalDate idJour, Utilisateur utilisateur, String legende, String name) {
//		GifTeleverse gifTeleverse = new GifTeleverse();
//		gifTeleverse.setDateHeureAjout(idJour);
//		gifTeleverse.setUtilisateur(utilisateur);
//		gifTeleverse.setLegende(legende);
//		gifTeleverse.setNomFichierOriginal(name);
//		return gifDao.save(gifTeleverse);
//	}
	@Override
	public GifTeleverse ajouterGifTeleverse(LocalDate localDate, Utilisateur utilisateur, String legende,
			String nomFichierOriginal) {
		GifTeleverse gifTeleverse = new GifTeleverse(utilisateur, jourDao.findByDate(localDate), legende,
				nomFichierOriginal, LocalDate.now());

		return gifTeleverseDao.save(gifTeleverse);
	}

}
