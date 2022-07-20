package fr.orsys.fx.calendrier_gif.service;

import java.util.List;

import fr.orsys.fx.calendrier_gif.business.Emotion;

public interface EmotionService {

	Emotion ajouterEmotion(Emotion emotion);
	Emotion ajouterEmotion(String nom, String code);

	List<Emotion> recupererEmotions();
	
	Emotion recupererEmotion(Long id);
	
	Emotion recupererEmotion(String nom);

	Emotion mettreAJourEmotion(Long id, String nom, String code);
	
	boolean supprimerEmotion(Long id);
	Emotion enregistrerEmotion(Emotion emotion);
}
