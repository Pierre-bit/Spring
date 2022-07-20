package fr.orsys.fx.calendrier_gif.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import fr.orsys.fx.calendrier_gif.business.Emotion;
import fr.orsys.fx.calendrier_gif.dao.EmotionDao;
import fr.orsys.fx.calendrier_gif.dao.ReactionDao;
import fr.orsys.fx.calendrier_gif.exceptions.EmotionExistanteException;
import fr.orsys.fx.calendrier_gif.service.EmotionService;

@Component // Spring va déduire que cette classe est un service
public class EmotionServiceImpl implements EmotionService {

	private final EmotionDao emotionDao;
	private final ReactionDao reactionDao;

	public EmotionServiceImpl(EmotionDao emotionDao, ReactionDao reactionDao) {
		super();
		this.emotionDao = emotionDao;
		this.reactionDao = reactionDao;
	}

	@Override
	public Emotion ajouterEmotion(String nom, String code) {
		if (emotionDao.findByCode(code)!=null) {
			throw new EmotionExistanteException("Le code de l'émotion est déjà présent en base");
		}
		return emotionDao.save(new Emotion(nom, code));
	}

	@Override
	public List<Emotion> recupererEmotions() {
		// Cette méthode se contente d'invoquer la méthode findAll sur l'objet
		// emotionDao
		return emotionDao.findAll();
	}

	@Override
	public Emotion recupererEmotion(Long id) {
		return emotionDao.findById(id).orElse(null);
	}

	@Override
	public Emotion recupererEmotion(String nom) {
		return emotionDao.findByNom(nom);
	}

	@Override
	public Emotion ajouterEmotion(Emotion emotion) {
		return emotionDao.save(emotion);
	}

	@Override
	public Emotion mettreAJourEmotion(Long id, String nom, String code) {
		Emotion emotion = recupererEmotion(id);
		emotion.setNom(nom);
		emotion.setCode(code);
		return emotionDao.save(emotion);
	}

	@Override
	@Transactional
	public boolean supprimerEmotion(Long id) {
		Emotion emotion = recupererEmotion(id);
		if (emotion == null) {
			return false;
		} else {
			reactionDao.deleteAllByEmotion(emotion);
			emotionDao.delete(emotion);
			return true;
		}
	}

	@Override
	public Emotion enregistrerEmotion(Emotion emotion) {
		return null;
	}

}
