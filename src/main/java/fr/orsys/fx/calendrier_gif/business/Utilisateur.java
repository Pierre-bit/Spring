package fr.orsys.fx.calendrier_gif.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Utilisateur {

	@ReadOnlyProperty
	private static final int NB_POINTS_INITIAL = 500;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "merci d indiquez votre nom")
	private String nom;
	@NotBlank(message = "merci d indiquez votre prénom")
	private String prenom;
	//@JsonProperty(access = Access.WRITE_ONLY)
	@Email(message = "Merci de préciser une adresse email au bon format")
	@NotBlank(message = "Merci de préciser une adresse email")
	@Column(unique = true)
	@Pattern(regexp = "^([A-Za-z0-9-])+(.[A-Za-z0-9-]+)*@orsys.fr$", message = "Votre adresse doit faire partie du nom de domaine orsys.fr")
	private String email;
	@NotBlank(message = "Merci de préciser un mot de passe")
	// On ne pourra pas lire le mot de passe en cas de get
	//@JsonProperty(access = Access.WRITE_ONLY)
	//@Size(min=3, message="Le mot de passe doit comporter au moins trois caractères")
	private String motDePasse;
	private int nbPoints;
	private LocalDateTime dateHeureInscription;
	@ManyToOne(fetch = FetchType.EAGER)
	
	private Theme theme;

	@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.REMOVE)
	// Pour ne pas ajouter un attribut dans le display dans l'objet
	@ToString.Exclude()
	@JsonIgnore
	private List<Gif> gifs;

	/**
	 * Constructeur par défaut avec précision du type de l'attribut
	 * dateHeureInscription et du nombre de points
	 */
	public Utilisateur() {
		dateHeureInscription = LocalDateTime.now();
		nbPoints = NB_POINTS_INITIAL;
	}

}
