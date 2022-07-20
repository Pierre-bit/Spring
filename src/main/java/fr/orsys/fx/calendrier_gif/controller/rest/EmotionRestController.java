package fr.orsys.fx.calendrier_gif.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.fx.calendrier_gif.business.Emotion;
import fr.orsys.fx.calendrier_gif.exceptions.EmotionExistanteException;
import fr.orsys.fx.calendrier_gif.service.EmotionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
public class EmotionRestController {

	// On déclare les dépendances du contrôleur REST
	private final EmotionService emotionService;
	
	/**
	 * Cette méthode renvoie la liste exhaustive des émotions
	 * Cette liste est sérialisée en Json car le contrôleur est de type RestController
	 * 
	 * @return
	 */
	@GetMapping("emotions")
	public List<Emotion> emotionsGet() {
		return emotionService.recupererEmotions();
	}

	/**
	 * Cette méthode permet l'ajout d'une émotion
	 * (la demande d'ajout utilise la méthode POST du protocole HTTP)
	 * 
	 * @param nom de l'émotion à ajouter
	 * @param code de l'émotion à ajouter
	 * @return un nouvel objet de type émotion
	 */
	@PostMapping("emotions/{nom}/{code}")
	@ResponseStatus(HttpStatus.CREATED)
	public Emotion emotionsPost(@PathVariable String nom, @PathVariable String code) {
		return emotionService.ajouterEmotion(nom, code);
	}
	
	@PostMapping("emotions")
	@ResponseStatus(HttpStatus.CREATED)
	public Emotion emotionsPostUtilisantLeCorpsDeLaRequete(@RequestBody Emotion emotion) {
		return emotionService.ajouterEmotion(emotion);
	}
	
	@PutMapping("emotions")
	public Emotion emotionsPut(@RequestBody Emotion emotion) {
		return emotionService.mettreAJourEmotion(emotion.getId(), emotion.getNom(), emotion.getCode());
	}
	
	@DeleteMapping("emotions/{id}")
	public boolean emotionsDelete(@PathVariable Long id) {
		return emotionService.supprimerEmotion(id);
	}
	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
    @ResponseStatus(code=HttpStatus.UNPROCESSABLE_ENTITY)
    public List<String> traiterDonneesInvalidesAvecDetails(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }
	
	/**
	 * Cette méthode sera invoqué si une exception de type EmotionExistanteException est levée
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(EmotionExistanteException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String traiterEmotionExistanteException(Exception e) {
		return e.getMessage();
	}

}