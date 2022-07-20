package fr.orsys.fx.calendrier_gif.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.orsys.fx.calendrier_gif.business.Reaction;
import fr.orsys.fx.calendrier_gif.business.Utilisateur;

public interface ReactionService {

	
	List<Reaction> recupererReactions();
	
	Reaction recupererReaction(Long id);
	
	Page<Reaction> recupererReactions(Pageable pageable);
	
	Reaction ajouterReaction(Reaction reaction,Utilisateur utilisateur);
}

