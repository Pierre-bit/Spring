package fr.orsys.fx.calendrier_gif.controller.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JourRestControllerTest {

	// Ce mockMvc va imitier le comportement de Swagger, Postman ou Angular
	@Autowired
	private MockMvc mockMvc;
	
	// NB : tout ce qui est déclaré en static n'est pas injecté par Spring
	private static String jourBrut = "2024-05-24";
	
	@Test
	@Order(1)
	public void testerAjouterJour() throws Exception {
		// l'objet mocké envoie une requete HTTP en utilsiant la methode post
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/jours/" + jourBrut);
		mockMvc.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.jsonPath("$.date").value(jourBrut))
			.andExpect(MockMvcResultMatchers.jsonPath("$.nbPoints").isNumber())
			// on verifie que le code retour est bien 201
			.andExpect(status().isCreated())
			// on affiche dans la console l'intégralité de la requete 
			.andDo(MockMvcResultHandlers.print());
	}

	 @Test
	 @Order(2)
     public void testerRecupererJours() throws Exception {

             MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/jours");
             mockMvc.perform(requestBuilder)
             .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(32))
             .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value("2022-07-01"))
             .andExpect(status().isOk())
             .andDo(MockMvcResultHandlers.print());

     }
	 
	 @Test
	 @DisplayName("Test patch jour avec nb points ok")
	 @Order(3)
     public void testerPatcherJour() throws Exception {

             MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/jours/" + jourBrut + "/42");
             mockMvc.perform(requestBuilder)
             .andExpect(MockMvcResultMatchers.jsonPath("$.nbPoints").value("42"))
             .andExpect(status().isOk())
             .andDo(MockMvcResultHandlers.print());

     }
	 
	 @Test
	 @DisplayName("Test patch jour avec nb points nok")
	 @Order(4)
     public void testerPatcherJourAvecNbPointsTropEleve() throws Exception {

             MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/jours/" + jourBrut + "/60");
             mockMvc.perform(requestBuilder)
             .andExpect(status().isUnprocessableEntity());
     }
	 
	 
	 @DisplayName("Test delete jours")
	 @Test
	 @Order(5)
	 public void testerDeleteJour() throws Exception
	 {
		 MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/jours/" + jourBrut);
		 mockMvc.perform(requestBuilder)
		 	.andExpect(status().is(200))
		 	// le methode de suppression renvoit un true
		 	.andExpect(MockMvcResultMatchers.content().string("true"))
			.andDo(MockMvcResultHandlers.print());
	 }
}