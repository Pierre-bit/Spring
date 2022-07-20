package fr.orsys.fx.calendrier_gif.business;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

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
@ToString(callSuper = true)

public class GifDistant extends Gif {

	@NotNull(message = "Merci de saisir une URL")
	@NotBlank(message = "Merci de saisir une URL")
	@URL(message = "Merci de saisir une URL valide, elle doit se terminer par .gif, .Gif ou .GIF")
	private String url;

	public GifDistant(Utilisateur utilisateur, Jour jour, String legende, String url, LocalDate dateHeureAjout) {
		super(dateHeureAjout, legende, utilisateur, jour);
		this.url = url;
	}

}
