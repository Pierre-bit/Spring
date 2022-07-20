package fr.orsys.fx.calendrier_gif.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import fr.orsys.fx.calendrier_gif.business.Gif;
import fr.orsys.fx.calendrier_gif.business.GifDistant;
import fr.orsys.fx.calendrier_gif.business.GifTeleverse;
import fr.orsys.fx.calendrier_gif.business.Jour;
import fr.orsys.fx.calendrier_gif.business.Utilisateur;

public interface GifService {

	GifDistant ajouterGifDistant(GifDistant gifDistant, Utilisateur utilisateur);

	GifTeleverse ajouterGifTeleverse(LocalDate localDate, Utilisateur utilisateur, String legende,
			String nomFichierOriginal, Jour jour);

	Gif recupererGifById(long id);

	GifTeleverse ajouterGifTeleverse(LocalDate idJour, Utilisateur utilisateur, String legende, String name);
	
	
}
