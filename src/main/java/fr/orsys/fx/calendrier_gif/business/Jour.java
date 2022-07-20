package fr.orsys.fx.calendrier_gif.business;

import java.time.LocalDate;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor

public class Jour {

	@Id
	private LocalDate date;
	@Max(value = 50)
	private int nbPoints;
	@OneToOne(mappedBy = "jour",cascade = CascadeType.REMOVE)
	@ToString.Exclude
	// pour eviter la recurrence de la relation
	@JsonIgnore
	private Gif gif;
	private static Random random = new Random();
	public Jour(@NonNull LocalDate date) {
		super();
		this.date = date;
		this.nbPoints = 10 + random.nextInt(30);
	}

}
