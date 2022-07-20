package fr.orsys.fx.calendrier_gif.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.orsys.fx.calendrier_gif.business.Theme;

public interface ThemeService {
	
	List<Theme> recupererThemes();;
	/**
	 * 
	 * @param id
	 * @return
	 */
	Theme recupererTheme(Long id);
	
	/**
	 * 
	 * @param id
	 * @param nom
	 * @return
	 */
	Theme recupererTheme(Long id, String nom);
	
	/**
	 * Cette méthode renvoie une page de Theme
	 * @param pageable
	 * @return
	 */
	Page<Theme> recupererTheme(Pageable pageable);
	
	Theme recupererTheme(String nom);
	
	
	

}
