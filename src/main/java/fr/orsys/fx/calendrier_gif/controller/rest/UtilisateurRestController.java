package fr.orsys.fx.calendrier_gif.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.fx.calendrier_gif.business.Utilisateur;
import fr.orsys.fx.calendrier_gif.dto.UtilisateurDto;
import fr.orsys.fx.calendrier_gif.exceptions.UtilisateurExistantException;
import fr.orsys.fx.calendrier_gif.service.ThemeService;
import fr.orsys.fx.calendrier_gif.service.UtilisateurService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
@Validated

public class UtilisateurRestController {

	private final UtilisateurService utilisateurService;
	private final ThemeService themeService;

	@GetMapping("users")
	public List<Utilisateur> userGet() {
		return utilisateurService.recupererUtilisateurs();
	}

	@PostMapping("users")
	@ResponseStatus(HttpStatus.CREATED)
	public Utilisateur userPost(@RequestBody Utilisateur user) {
		return utilisateurService.enregistrerUtilisateur(user);
	}

	@DeleteMapping("users/{id}")
	public boolean userDelete(@PathVariable Long id) {
		return utilisateurService.supprimerUser(id);
	}

	@PutMapping("users")
	public Utilisateur userPut(@RequestBody Utilisateur user) {
		return utilisateurService.updateUser(user.getId(), user.getNom(), user.getPrenom(), user.getEmail(),
				user.getMotDePasse(), user.getNbPoints());
	}

	@GetMapping("users/{id}")
	public Utilisateur getOneUser(@PathVariable Long id) {
		return utilisateurService.recupererUser(id);
	}

	@GetMapping("users/all")
	public Page<Utilisateur> UtilisateurPaginesAndFilter(@PageableDefault(size = 15,sort="id") Pageable pageable,
			@RequestParam(required = false) String filter) {

		return utilisateurService.recupererUtilisateurs(pageable, filter);
	}
	
	@PostMapping(value = "utilisateursDto")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Utilisateur ajouterUtilisateur(@Valid @RequestBody UtilisateurDto utilisateurDto , BindingResult result) {

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setTheme(themeService.recupererTheme(utilisateurDto.getIdTheme()));
        return utilisateurService.enregistrerUtilisateur(utilisateur);
    }
	// exceptionhandler est toujours dans le controlleurs
	@ExceptionHandler(UtilisateurExistantException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String UtilisateurExistanteException(Exception e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
    @ResponseStatus(code=HttpStatus.UNPROCESSABLE_ENTITY)
    public List<String> traiterDonneesInvalidesAvecDetails(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }



}
