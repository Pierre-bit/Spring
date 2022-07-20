package fr.orsys.fx.calendrier_gif.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.orsys.fx.calendrier_gif.business.Jour;
import fr.orsys.fx.calendrier_gif.business.Theme;
import fr.orsys.fx.calendrier_gif.business.Utilisateur;

public interface JourDao extends JpaRepository<Jour, LocalDate> {
	
	@Query(value = "FROM Jour WHERE month(date) = month(current_date()) and year(date) = year(current_date()) ORDER BY nbPoints DESC")
	List<Jour> findAllDayByMonth();
	
	//écrire une méthode qui donne le dernier jour stocké en base
	@Query(value = "SELECT max(date) FROM Jour")
	LocalDate FindLastDayToMonth();
	
	//écrire une méthode qui renvoie les jours mélangés de manière aléatoire
	@Query(value = "FROM Jour ORDER BY rand() ")
	List<Jour> FindRandDay();
	
//	exercice suivant : dans JourDao, ajouter une méthode possédant deux paramètres annee et mois.
//	Cette méthode renvoie le nombre moyen de points des jours
	@Query(value = "SELECT AVG(nbPoints) FROM Jour WHERE year(date)=:year AND month(date)=:month ")
	Integer findAverageNbPoints(@Param("year") int year, @Param("month") int month);

	Jour findByDate(LocalDate localDate);
	
	
	
	
	
}
