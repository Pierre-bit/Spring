package fr.orsys.fx.calendrier_gif.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.orsys.fx.calendrier_gif.business.Gif;
import fr.orsys.fx.calendrier_gif.util.NbGifs;
import fr.orsys.fx.calendrier_gif.util.NbInscrits;

public interface GifDao extends JpaRepository<Gif, Long> {

	
	//écrire la méthode qui donne les gifs triés sur le nombre de réactions décroissant
	@Query(value = "FROM Gif g ORDER BY  size(g.reactions) DESC")
	List<Gif> FindGifSortByReactions();
	
	//écrire une méthode qui donne le nombre de gifs ajoutés chaque mois (en utilisant une classe util NbGifs) 
	@Query(value = "SELECT new fr.orsys.fx.calendrier_gif.util.NbGifs(year(g.dateHeureAjout), "
			+ "month(g.dateHeureAjout), COUNT(*) as nbgifs) "
			+ "FROM Gif g GROUP BY year(g.dateHeureAjout), month(g.dateHeureAjout)" )
	List<NbGifs> findNbGifs();
	
	List<Gif> findByLegendeContaining(String legende);
	
	
}

