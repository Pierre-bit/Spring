package fr.orsys.fx.calendrier_gif.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.orsys.fx.calendrier_gif.business.Theme;
import fr.orsys.fx.calendrier_gif.service.ThemeService;
import lombok.AllArgsConstructor;
import fr.orsys.fx.calendrier_gif.dao.JourDao;
import fr.orsys.fx.calendrier_gif.dao.ThemeDao;
import fr.orsys.fx.calendrier_gif.dao.UtilisateurDao;

@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService {

	private ThemeDao themeDao;
	
	@Override
	public List<Theme> recupererThemes() {
		return themeDao.findAll();
	}

	@Override
	public Theme recupererTheme(Long id) {
		return themeDao.findById(id).orElse(null);
	}

	@Override
	public Theme recupererTheme(Long id, String nom) {
		return themeDao.findByIdAndNom(id,nom);
	}

	@Override
	public Page<Theme> recupererTheme(Pageable pageable) {
		return themeDao.findAll(pageable);
	}

	@Override
	public Theme recupererTheme(String nom) {
		return themeDao.findByNom(nom);
	}

	

}
