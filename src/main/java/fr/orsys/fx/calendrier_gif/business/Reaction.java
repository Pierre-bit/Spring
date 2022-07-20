package fr.orsys.fx.calendrier_gif.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Reaction {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime dateHeure;
	@ManyToOne
	private Emotion emotion;
	@ManyToOne
	private Gif gif;
	@ManyToOne
	private Utilisateur utilisateur;
	
	public Reaction(Utilisateur utilisateur, Emotion emotion, Gif gif) {
		this.utilisateur = utilisateur;
		this.emotion = emotion;
		this.gif = gif;
	}

	
	

	

	
	
}
