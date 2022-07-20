package fr.orsys.fx.calendrier_gif.business;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;

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
public class GifTeleverse extends Gif {

	public GifTeleverse(Utilisateur utilisateur, Jour jour, String legende, String nomFichierOriginal, LocalDate dateHeureAjout) {
        super(dateHeureAjout,legende,utilisateur,jour);
        this.nomFichierOriginal= nomFichierOriginal;
}

	private String nomFichierOriginal;


	
}
