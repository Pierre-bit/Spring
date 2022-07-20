package fr.orsys.fx.calendrier_gif.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.orsys.fx.calendrier_gif.business.Utilisateur;
import fr.orsys.fx.calendrier_gif.util.NbInscrits;

@RepositoryRestResource
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {

//	List<Utilisateur> findByThemeAndDateHeureInscriptionBetween(Theme)
	@Query(value = "FROM Utilisateur WHERE theme.nom = 'Dark'")
	List<Utilisateur> findUsersHavingChosenDarkTheme();

	@Query(value = "FROM Utilisateur WHERE id NOT IN (SELECT DISTINCT utilisateur.id FROM Gif)")
	List<Utilisateur> findNonContribuitingUsers();

	@Query(value = "FROM Utilisateur ORDER BY prenom")
	List<Utilisateur> findAllUsersSortedByPrenom();

//  Requête HQL qui donne le nom et le prénom des utilisateurs s’étant inscrits au mois de juillet 2022 et dont l’adresse email email se termine par @orsys.fr
	@Query("FROM Utilisateur WHERE month(dateHeureInscription)='7' AND year(dateHeureInscription)='2022' AND email LIKE '%@orsys.fr'")
	List<Utilisateur> findInscriptionJulyUsers();

	@Query(value = "FROM Utilisateur WHERE email=:email AND motDePasse=:motDePasse")
	Utilisateur findUserWithMailAndPwd(@Param("email") String email, @Param("motDePasse") String motDePasse);

	@Query(value = "SELECT new fr.orsys.fx.calendrier_gif.util.NbInscrits(year(u.dateHeureInscription), "
			+ "month(u.dateHeureInscription), COUNT(*) as nbutilisateurs) FROM Utilisateur"
			+ " u GROUP BY year(u.dateHeureInscription), month(u.dateHeureInscription)")
	List<NbInscrits> findNbInscrits();
	
//	écrire une méthode qui donne les utilisateurs triés
//	sur la date et heure d'inscription et ayant le prénom donné en paramètre
	@Query(value = "FROM Utilisateur WHERE prenom=:prenom ORDER BY dateHeureInscription ")
	List<Utilisateur> FindDateAndHoursByName(@Param("prenom") String prenom );
	
	Utilisateur findByEmailAndMotDePasse(String email,String motDePasse);

	// utilisation d'une méthode par dérivation
	Page<Utilisateur> findByNomStartingWithIgnoreCase(Pageable pageable, String filter);
	
	// utilisation d'une méthode par dérivation
    Page<Utilisateur> findByPrenomStartingWithAndDateHeureInscriptionBetween(Pageable pageable, 
            String filtre,
            LocalDateTime dateHeureDebut,
            LocalDateTime dateHeureFin
            );
    // utilisation d'une méthode par dérivation
    Page<Utilisateur> findByPrenomStartingWithAndDateHeureInscriptionBetweenAndGifsIsEmpty(Pageable pageable, 
            String filtre,
            LocalDateTime dateHeureDebut,
            LocalDateTime dateHeureFin
            );

    Utilisateur findByEmail(@Valid String email);

	

	

}
