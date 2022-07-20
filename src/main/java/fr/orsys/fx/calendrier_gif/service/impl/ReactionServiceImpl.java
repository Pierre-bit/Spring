package fr.orsys.fx.calendrier_gif.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.orsys.fx.calendrier_gif.business.Reaction;
import fr.orsys.fx.calendrier_gif.business.Utilisateur;
import fr.orsys.fx.calendrier_gif.dao.ReactionDao;
import fr.orsys.fx.calendrier_gif.service.ReactionService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReactionServiceImpl implements ReactionService {

	private ReactionDao reactionDao;
	@Override
	public List<Reaction> recupererReactions() {
		return reactionDao.findAll();
	}

	@Override
	public Reaction recupererReaction(Long id) {
		return reactionDao.findById(id).orElse(null);
	}

	@Override
	public Page<Reaction> recupererReactions(Pageable pageable) {
		return reactionDao.findAll(pageable);
	}

	@Override
	public Reaction ajouterReaction(Reaction reaction,Utilisateur utilisateur ) {
		reaction.setDateHeure(LocalDateTime.now());
		reaction.setUtilisateur(utilisateur);
		return reactionDao.save(reaction);
	}
	
	

}
