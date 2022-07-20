package fr.orsys.fx.calendrier_gif.controller.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.fx.calendrier_gif.business.Jour;
import fr.orsys.fx.calendrier_gif.exceptions.JourExistantException;
import fr.orsys.fx.calendrier_gif.exceptions.JourInexistantException;
import fr.orsys.fx.calendrier_gif.exceptions.UtilisateurExistantException;
import fr.orsys.fx.calendrier_gif.service.JourService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
public class JourRestController {

	// On déclare les dépendances du contrôleur REST
	private final JourService jourService;

	
	@GetMapping("jours")
	public List<Jour> jourGet() {
		return jourService.recupererJours();
	}

	@PostMapping("jours")
	@ResponseStatus(HttpStatus.CREATED)
	public Jour jourPost(@RequestBody LocalDate date) {
		return jourService.ajouterJour(date);

	}

	@PutMapping("jours")
	public Jour joursPut(@RequestBody Jour jour) {
		return jourService.updateJour(jour.getDate(), jour.getNbPoints());
	}
	
	   // Méthode qui demande la mise à jour partielle d'un objet (ex : uniquement le nb de points)
    @PatchMapping("jours/{date}/{nouveauNbPoints}")
    public Jour joursPatch(@PathVariable @DateTimeFormat(iso=ISO.DATE) final LocalDate date, @PathVariable int nouveauNbPoints) throws JourInexistantException {
        return jourService.mettreAJour(date, nouveauNbPoints);
    }

	@DeleteMapping("jours/{idJour}")
	@ResponseStatus(HttpStatus.OK)
	public boolean jourDelete(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idJour) {
		jourService.jourDelete(idJour);
		return true;
	}
	
	@GetMapping("jours/{idJour}")
	public Jour getOneJour(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idJour) {
		return jourService.recupererJour(idJour);
	}
	
	@PostMapping("joursAvecResponseEntity")
	public ResponseEntity<Jour> joursPostVersion2(@RequestBody Jour jour) throws URISyntaxException {

		// Utilisation du patron builder
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append('c')
				.append(0)
				.append("Test");
				
	     String resultat = stringBuilder.append(jour).toString();
		System.out.println(resultat);
		
		if (jour.getDate()==null) {
			return ResponseEntity.badRequest().body(null);
				//	ResponseEntity.status(400).body(null);
		}
		
		if (jourService.recupererJour(jour.getDate()) != null) {
			throw new JourExistantException("Ce jour existe déjà en base");
		}
		
		Jour jourPersistant = jourService.enregistrerJour(jour);
		return ResponseEntity.created(new URI("/api/jours/" + jourPersistant.getDate()))
						     .body(jourPersistant);
	}
	
	  // Ajouter un jour -> POST 
    @PostMapping("jours/{date}")
    @ResponseStatus(code=HttpStatus.CREATED)
    public Jour joursPost(@PathVariable @DateTimeFormat(iso=ISO.DATE) final LocalDate date) {
        return jourService.ajouterJour(date);
        
    }
    
 // exceptionhandler est toujours dans le controlleurs
 	@ExceptionHandler(JourExistantException.class)
 	@ResponseStatus(HttpStatus.CONFLICT)
 	public String JourExistantException(Exception e) {
 		return e.getMessage();
 	}
 	
 	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
     @ResponseStatus(code=HttpStatus.UNPROCESSABLE_ENTITY)
     public List<String> traiterDonneesInvalidesAvecDetails(ConstraintViolationException exception) {
         return exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
     }

}
