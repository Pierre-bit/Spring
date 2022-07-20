package fr.orsys.fx.calendrier_gif.automatism;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.orsys.fx.calendrier_gif.business.Jour;
import fr.orsys.fx.calendrier_gif.service.JourService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JourAutomatism {

	private final JourService jourService;
	
	@Scheduled(cron=" * * * * * *")
	private void ajouterJours()
	{
		Jour JourSuivant = new Jour(jourService.LastDayOFBase().plusDays(1));
		jourService.ajouterJourSuivant(JourSuivant);
		System.out.println(jourService.LastDayOFBase().plusDays(1));
		
	}
}
