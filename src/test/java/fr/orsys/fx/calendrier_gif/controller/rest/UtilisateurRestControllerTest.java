package fr.orsys.fx.calendrier_gif.controller.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.orsys.fx.calendrier_gif.business.Utilisateur;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UtilisateurRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	// on demande a spring dont la portee est prototype
	// spring va creer une instance a chaque itération
	private ObjectMapper objectMapper;

	private static Utilisateur utilisateur = new Utilisateur();

	@Test
	@Order(1)
	void testUserPost() throws Exception {

		utilisateur.setId(1L);
		utilisateur.setNom("toto");
		utilisateur.setPrenom("tata");
		utilisateur.setEmail("tester@orsys.fr");
		utilisateur.setMotDePasse("1234");
		String utilisateurJSON = objectMapper.writeValueAsString(utilisateur);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users")
				.content(utilisateurJSON).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(utilisateur.getNom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value(utilisateur.getPrenom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(utilisateur.getEmail()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nbPoints").value(500)).andExpect(status().isCreated());

	}

	void testAjouterUtilisateur() {
		Utilisateur utilisateur = new Utilisateur();
		try {
			objectMapper.writeValueAsString(utilisateur);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(2)
	void testUserGet() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users");
		mockMvc.perform(requestBuilder)
				// pour verifier le nombre d'utilisateurs prensent en bdd
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(102)).andExpect(status().isOk());
//	         .andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(3)
	void testGetOneUser() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/" + "109");
		mockMvc.perform(requestBuilder)
				// on test les valeurs correspond a l'id de l'utilisateurs
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(utilisateur.getNom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value(utilisateur.getPrenom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(utilisateur.getEmail()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nbPoints").value(500)).andExpect(status().isOk());
//	         .andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(4)
	void testUtilisateurPaginesAndFilter() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/all?page=0&size=20");
		mockMvc.perform(requestBuilder)
				// pour verifier le nombre d'utilisateurs prensent en bdd
				.andExpect(MockMvcResultMatchers.jsonPath("$.size").value(20)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
				
				/**
				 * autre version 
				 * .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(10))
				 * .andExpect(status().isOk())	
				 * .andDo(MockMvcResultHandlers.print());
				 */
				
	}

	@Test
	@Order(5)
	void testUserPut() throws Exception {
		utilisateur.setId(109L);
		utilisateur.setNom("titi");
		utilisateur.setPrenom("pierre");
		utilisateur.setEmail("pierre@orsys.fr");
		utilisateur.setMotDePasse("123447");
		String utilisateurJSON = objectMapper.writeValueAsString(utilisateur);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/users")
		.content(utilisateurJSON)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(utilisateur.getNom()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value(utilisateur.getPrenom()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(utilisateur.getEmail()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.nbPoints").value(500)).andExpect(status().isOk());
	}

	@Test
	@Order(6)
	void testUserDelete() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/users/" + "109");
		mockMvc.perform(requestBuilder)
		.andExpect(status().is(200))
	 	// le methode de suppression renvoit un true
	 	.andExpect(MockMvcResultMatchers.content().string("true"))
		.andDo(MockMvcResultHandlers.print());
		
	}

}
