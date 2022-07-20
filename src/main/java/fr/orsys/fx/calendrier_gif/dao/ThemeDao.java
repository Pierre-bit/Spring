package fr.orsys.fx.calendrier_gif.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.orsys.fx.calendrier_gif.business.Theme;
@RepositoryRestResource
public interface ThemeDao extends JpaRepository<Theme, Long> {

	@Query("FROM Theme where nom like'B%'")
	List<Theme> findThemeStartingByB();

	@Query("FROM Theme WHERE nom=:nom")
	Theme findByNom(@Param("nom") String nom);

	Theme findByIdAndNom(Long id, String nom);

}
