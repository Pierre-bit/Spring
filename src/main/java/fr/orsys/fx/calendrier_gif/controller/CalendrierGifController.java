package fr.orsys.fx.calendrier_gif.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fr.orsys.fx.calendrier_gif.business.Gif;
import fr.orsys.fx.calendrier_gif.business.GifDistant;
import fr.orsys.fx.calendrier_gif.business.GifTeleverse;
import fr.orsys.fx.calendrier_gif.business.Jour;
import fr.orsys.fx.calendrier_gif.business.Reaction;
import fr.orsys.fx.calendrier_gif.business.Utilisateur;
import fr.orsys.fx.calendrier_gif.service.EmotionService;
import fr.orsys.fx.calendrier_gif.service.GifService;
import fr.orsys.fx.calendrier_gif.service.JourService;
import fr.orsys.fx.calendrier_gif.service.ReactionService;
import fr.orsys.fx.calendrier_gif.service.ThemeService;
import fr.orsys.fx.calendrier_gif.service.UtilisateurService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class CalendrierGifController {

	private static final int NB_JOURS_PAR_PAGE = 7;

	protected static final String DOSSIER_IMAGES = "src/main/webapp/images/";

	// On déclares les dépendances du constructeur
	private final EmotionService emotionService;
	private final JourService jourService;
	private final UtilisateurService utilisateurService;
	private final ThemeService themeService;
	private final GifService gifService;
	private final ReactionService reactionService;
	private final HttpSession httpSession;

	@RequestMapping(value = { "/", "/index" })
	public ModelAndView connexionGet() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

	// Cette méthode du contrôleur est annotée @PostMapping (annotation de Spring
	// 4.3 en 2016)
	// car le formulaire HTML utilise la méthode post : via l'attribut method de la
	// balise form
	// <form action="connexion" method="post">
	// En post : on envoie les données saisies sur le formulaire HTML dans le corps
	// de la requête
	// En cliquant sur le bouton "Connexion", la requête HTTP est envoyée vers l'URL
	// connexion
	// c'est pour cette raison que l'attribut value est égal à connexion
	@PostMapping(value = "connexion")
	public ModelAndView connexionPost(@RequestParam("EMAIL") String email,
			@RequestParam("MOT_DE_PASSE") String motDePasse) {

		// On essaie de récupérer l'utilisateur avec les infos saisies sur le formulaire
		// HTML de connexion
		Utilisateur utilisateur = utilisateurService.recupererUtilisateur(email, motDePasse);

		if (utilisateur == null) {
			ModelAndView mav = new ModelAndView("redirect:index");
			mav.addObject("notification", "Mot de passe et/ou email incorrect");
			return mav;
		} else {
			// L'utilisateur a fourni les bonnes informations (email et mot de passe)

			// On ajoute en session HTTP l'objet utilisateur
			httpSession.setAttribute("utilisateur", utilisateur);

			// On redirige vers l'URL calendrier
			// équivalent de ce que l'on faisait à l'époque de Jakarta Enteprise en écrivant
			// :
			// response.sendRedirect("calendrier");
			// Après le mot redirect, on fait référence à une URL et non pas à une vue
			ModelAndView mav = new ModelAndView("redirect:calendrier");
			return mav;
		}
	}

	@GetMapping("inscription")
	public ModelAndView inscriptionGet(@ModelAttribute Utilisateur utilisateur) {
		ModelAndView mav = new ModelAndView("inscription");
		mav.addObject("themes", themeService.recupererThemes());
		return mav;
	}

	@PostMapping("inscription")
	public ModelAndView inscriptionPost(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult result) {
		if (result.hasErrors()) {
			// La validation de l’objet utilisateur par rapport aux contraintes
			// de validation définies dans la classe métier Utilisateur a produit
			// des erreurs
			return inscriptionGet(utilisateur);
		} else {
			utilisateurService.enregistrerUtilisateur(utilisateur);
			ModelAndView mav = new ModelAndView("redirect:index");
			mav.addObject("notification", "Utilisateur ajouté");
			return mav;
		}
	}

	/**
	 * Cette méthode redirige vers la vue emotion.jsp
	 * 
	 * @return
	 */
	@GetMapping("emotion")
	public ModelAndView emotionGet() {
		System.out.println(new Date());
		return new ModelAndView("emotion");
	}

	@PostMapping("emotion")
	public ModelAndView emotionPost(@RequestParam("NOM") String nom, @RequestParam("CODE") String code) {
		emotionService.ajouterEmotion(nom, code);

		return new ModelAndView("redirect:emotions");
	}

//    @DeleteMapping(value = "/posts/{id}")
//    public ResponseEntity<Long> deletePost(@PathVariable Long id) {
//
//        var isRemoved = postService.delete(id);
//
//        if (!isRemoved) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(id, HttpStatus.OK);
//    }
	@GetMapping("deleteemotion")
	public ModelAndView DeleteEmotion(@RequestParam("id") Long id) {
		emotionService.supprimerEmotion(id);
		return new ModelAndView("redirect:emotions");
	}

	// Toutes les méthodes de ce conrôleur devront renvoyer un objet de type
	// ModelAndView
	// (métaphore de la Danette Pop)
	// On indique sur quelle(s) URL(s) la méthode va écouter
	// Autrement dit : quelle(s) sont la ou les URLs que la méthode prend en charge
	// Equivalent de @WebServlet
	@RequestMapping(value = { "/emotions" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView emotions() {

		System.out.println(new Date());
		// On déclare et on instancie un objet de type ModelAndView
		ModelAndView mav = new ModelAndView();

		// On définit le nom de la vue (== crème dessert)
		// Equivalent de request.getRequestDispatcher("WEB-INF/index.jsp")
		mav.setViewName("emotions");

		// On ajoute dans le modèle la liste des nbs d'inscrits
		// Equivalent de request.setAttribute("nbInscrits",
		// utilisateurService.recupererNbInscrits())
		mav.addObject("emotions", emotionService.recupererEmotions());

		return mav;
	}

	@GetMapping("calendrier/placerGifDistant")
	public ModelAndView placerGifDistantGet(
			@RequestParam("ID_JOUR") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idJour,
			@ModelAttribute GifDistant gifDistant) {

		ModelAndView mav = new ModelAndView("placerGifDistant");
		Jour jour = jourService.recupererJour(idJour);
		// On associe le jour choisi au gif
		gifDistant.setJour(jour);
		return mav;
	}

	@PostMapping("calendrier/placerGifDistant")
	public ModelAndView placerGifDistantPost(@ModelAttribute GifDistant gifDistant, BindingResult result) {
		if (result.hasErrors()) {
			// La validation de l’objet utilisateur par rapport aux contraintes
			// de validation définies dans la classe métier Utilisateur a produit
			// des erreurs
			return placerGifDistantGet(gifDistant.getJour().getDate(), gifDistant);
		} else {
			Utilisateur utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");
			gifService.ajouterGifDistant(gifDistant, utilisateur);
			ModelAndView mav = new ModelAndView("redirect:/calendrier");
			mav.addObject("notification", "Gif distant ajouté");
			return mav;
		}
	}

	@GetMapping("calendrier/reagir")
	public ModelAndView placerReactionGet(@ModelAttribute Reaction reaction, @RequestParam("ID_GIF") Long idGif) {

		ModelAndView mav = new ModelAndView("reagir");
		mav.addObject("emotions", emotionService.recupererEmotions());
		mav.addObject("gif", gifService.recupererGifById(idGif));
		Gif gif = gifService.recupererGifById((idGif));
		reaction.setGif(gif);
		return mav;
	}

	@PostMapping("calendrier/reagir")
	public ModelAndView placerReactionPost(@ModelAttribute Reaction reaction, BindingResult result) {
		System.out.println(reaction);
		if (result.hasErrors()) {
			return placerReactionGet(reaction, reaction.getGif().getId());
		} else {
			System.out.println("bon");
			reactionService.ajouterReaction(reaction, (Utilisateur) httpSession.getAttribute("utilisateur"));
			return new ModelAndView("redirect:/calendrier");
		}
	}

	@GetMapping("calendrier/televerserGif")
	public ModelAndView placerGifTeleverseGet(@ModelAttribute GifTeleverse gifTeleverse,
			@RequestParam("ID_JOUR") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idJour) {
		ModelAndView mav = new ModelAndView("televerserGif");
		Jour jour = jourService.recupererJour(idJour);
		// On associe le jour choisi au gif
		gifTeleverse.setJour(jour);
		mav.addObject("idJour", jour.getDate());
		return mav;

	}

//	@PostMapping("calendrier/televerserGif")
//	public ModelAndView placerGifTeleversePost(@ModelAttribute GifTeleverse gifTeleverse,
//			@RequestParam("FICHIER") MultipartFile multipartFile)
//	{
//		//on verifie sur le fichier est vide
//		if (multipartFile.isEmpty())
//		{
//			return placerGifTeleverseGet(gifTeleverse, gifTeleverse.getJour().getDate());
//		}
//		
//		try {
//			// on commence par ernregistrer le fichier
//			enregisterFichier(multipartFile.getOriginalFilename(), multipartFile);
//			// on implement la méthode ajouterGifGTeleverse avec ses parametre
//			gifService.ajouterGifTeleverse(LocalDateTime.now(), (Utilisateur) httpSession.getAttribute("utilisateur"),
//					gifTeleverse.getLegende(),multipartFile.getOriginalFilename() , gifTeleverse.getJour());
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return new ModelAndView("redirect:/calendrier");
//	}
	
	@PostMapping("calendrier/televerserGif")
    public ModelAndView placerGifTeleversePost(
            @RequestParam("ID_JOUR") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idJour,
            @RequestParam("LEGENDE") String legende, @RequestParam("FICHIER") MultipartFile file) throws IOException {

        Utilisateur utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");
        
        GifTeleverse gifTeleverse = gifService.ajouterGifTeleverse(idJour, utilisateur, legende, file.getName());
        
        enregisterFichier(gifTeleverse.getId().toString()+".gif", file);
        int nbPointsRestant= utilisateur.getNbPoints()-gifTeleverse.getJour().getNbPoints();
        System.out.println(nbPointsRestant);
        

        return new ModelAndView("redirect:/calendrier");
    }

	@GetMapping("calendrier")
	public ModelAndView calendrier(@PageableDefault(page = 0, size = NB_JOURS_PAR_PAGE) Pageable pageable) {
		ModelAndView mav = new ModelAndView();
		// On définit le nom de la vue (la crème dessert)
		// Le mav utilise la vue calendrier.jsp
		mav.setViewName("calendrier");

		// On ajout un objet dans le compartiment à petites billes
		// Les attributs du mav seront envoyés à la vue calendrier.jsp
		// avant on écrivait : request.setAttribute("pageDeJours",
		// jourService.recupererJours(pageable))
		mav.addObject("pageDeJours", jourService.recupererJours(pageable));
		return mav;
	}
	
	

	protected static void enregisterFichier(String nom, MultipartFile multipartFile) throws IOException {
		Path chemin = Paths.get(DOSSIER_IMAGES);
		multipartFile.getOriginalFilename();
		if (!Files.exists(chemin)) {
			Files.createDirectories(chemin);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path cheminFichier = chemin.resolve(nom);
			Files.copy(inputStream, cheminFichier, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Erreur d'écriture : " + nom, ioe);
		}
	}

}