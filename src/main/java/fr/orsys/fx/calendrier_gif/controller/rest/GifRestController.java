package fr.orsys.fx.calendrier_gif.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.orsys.fx.calendrier_gif.business.Gif;
import fr.orsys.fx.calendrier_gif.service.EmotionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
public class GifRestController {

	
//	@JsonProperty("urlOuNomFichierOriginal")
//    public String getUrlOuNomFichierOriginal() {
//        if (Gif.class.getSimpleName()=="GifDistant") {
//            
//        }
//		return null;
//    }
//	
//	@JsonProperty("urlOuNomFichierOriginal")
//    public String getUrlOuNomFichierOriginal() {
//        if (gif.getClass().equals(GifDistant.class)) {
//            return ((GifDistant) gif).getUrl();
//        }
//        return ((GifTeleverse) gif).getNomFichierOriginal();
//        
//    }
}
