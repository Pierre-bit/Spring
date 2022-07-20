package fr.orsys.fx.calendrier_gif.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UtilisateurDto {

    String nom;
    
    @NotBlank(message="Merci de préciser votre prénom")
    String prenom;
    
    @Email(message="Merci de préciser une adresse email au bon format")
    @NotBlank(message="Merci de préciser une adresse email")
    @Pattern(regexp="^([A-Za-z0-9-])+(.[A-Za-z0-9-]+)*@orsys.fr$", message="Votre adresse doit faire partie du nom de domaine orsys.fr")
    String email;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    @Size(min=3, message="{Utilisateur.motDePasse}")
    String motDePasse;
    
    @NotNull(message="Merci de choisir un thème")
    Long idTheme;
    
    int nbPoints;
    
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    LocalDate dateDeNaissance;

}