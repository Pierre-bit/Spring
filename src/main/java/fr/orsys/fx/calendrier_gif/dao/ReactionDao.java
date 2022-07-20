package fr.orsys.fx.calendrier_gif.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.orsys.fx.calendrier_gif.business.Emotion;
import fr.orsys.fx.calendrier_gif.business.Gif;
import fr.orsys.fx.calendrier_gif.business.Reaction;

public interface ReactionDao extends JpaRepository<Reaction, Long> {

	@Query("SELECT r FROM Reaction r WHERE r.gif.jour.date >= :dateDebut and r.gif.jour.date < :dateFin")
	List<Reaction> findAllReactionsByDayBetween(@Param("dateDebut") LocalDateTime dateDebut,
			@Param("dateFin") LocalDateTime dateFin);

//	dans ReactionDao, écrire la méthode qui donne les 5 dernières réactions au gif du jour donné en paramètre 
	List<Reaction> findLast5ByGif(Gif gif);

	void findLast5ReactionByGif(Gif gif);

	//List<Reaction> findLast5ByGifJourOrderByDateHeure(Jour jour);
	
	//dans ReactionDao, écrire la méthode qui donne les 5 dernières 
	//réactions au gif du jour dont la date est donnée en paramètre
	List<Reaction> findLast5ByGifJourDateOrderByDateHeure(LocalDate localDate);
	
	/**
	 * Cette methode de type Integer supprime toutes les reactions liées en utilisant l'emotion donné en parametre
	 * @param emotion
	 * @return
	 */
	Integer deleteAllByEmotion(Emotion emotion);

}
