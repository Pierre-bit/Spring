package fr.orsys.fx.calendrier_gif.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
public class Gif {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	protected LocalDate dateHeureAjout;
	protected String legende;
	@OneToMany(mappedBy = "gif")
	@ToString.Exclude
	private List<Reaction> reactions;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToOne
	protected Jour jour;
	
	public Gif(LocalDate dateHeureAjout, String legende, Utilisateur utilisateur,
			Jour jour) {
		super();
		this.dateHeureAjout = dateHeureAjout;
		this.legende = legende;
		this.utilisateur = utilisateur;
		this.jour = jour;
	}
	

	

}
