package com.b2wplanets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import document.Planet;
import repository.PlanetRepository;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class APITest {
	@Autowired
	private PlanetRepository planetRepository;
	@Autowired
	private WebApplicationContext context;
	MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
		
		planetRepository.deleteAll();
	}
	
	public Planet resultGet(String uri) throws Exception {
		MvcResult result = mockMvc.perform(get(uri))
		        .andExpect(status().isOk())
		        .andReturn();
		
		ObjectMapper mapper = new ObjectMapper();
	     
		return mapper.readValue(result.getResponse().getContentAsString(), Planet.class);
	}

	@Test
	public void getPlanetById() throws Exception {
		
		Planet db_planet = new Planet();
		db_planet.setId(0L);
		db_planet.setName("Alderaan");
		db_planet.setClimate("temperate");
		db_planet.setTerrain("grasslands, mountains");
		planetRepository.save(db_planet);
		
		db_planet.setId(3L);
		db_planet.setName("teste name");
		db_planet.setClimate("temperate");
		db_planet.setTerrain("grasslands, mountains");
		planetRepository.save(db_planet);
		
		
		Planet planet = resultGet("/planet/id/0");
		assertEquals("Alderaan", planet.getName());
		
		planet = resultGet("/planet/id/3");
		assertEquals("Teste name", planet.getName());
	}

	@Test
	public void getPlanetByName() throws Exception {
		
		Planet db_planet = new Planet();
		db_planet.setId(0L);
		db_planet.setName("Alderaan");
		db_planet.setClimate("temperate");
		db_planet.setTerrain("grasslands, mountains");
		planetRepository.save(db_planet);
		
		
		Planet planet = resultGet("/planet/name/Alderaan");
	    assertEquals("Alderaan", planet.getName());
	    
	    planet = resultGet("/planet/name/alderaan");
	    assertEquals("Alderaan", planet.getName());
	    
	    planet = resultGet("/planet/name/ALDERAAN");
	    assertEquals("Alderaan", planet.getName()); 
	}

	@Test
	public void deletePlanetById() throws Exception {

		Planet db_planet = new Planet();
		db_planet.setId(0L);
		db_planet.setName("Alderaan");
		db_planet.setClimate("temperate");
		db_planet.setTerrain("grasslands, mountains");
		planetRepository.save(db_planet);
		
		mockMvc.perform(delete("/planet/id/0"))
		        .andExpect(status().isOk());
		
		mockMvc.perform(delete("/planet/id/567"))
        .andExpect(status().isNotFound());
	}
	
	@Test
	public void createPlanet() throws Exception {

		Planet PlanetToCreate = new Planet();
		PlanetToCreate.setName("Alderaan");
		PlanetToCreate.setClimate("temperate");
		PlanetToCreate.setTerrain("grasslands, mountains");

		ObjectMapper mapper = new ObjectMapper();

		this.mockMvc.perform(post("/planet/")
				.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(PlanetToCreate)))
                .andExpect(status().isCreated());
		
		
		this.mockMvc.perform(post("/planet/")
				.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(PlanetToCreate)))
                .andExpect(status().isConflict());
		
	}
	
	@Test
	public void notCreatePlanetByTerrain() throws Exception {

		Planet PlanetToCreate = new Planet();
		PlanetToCreate.setName("Alderaan");
		PlanetToCreate.setClimate("temperate");

		ObjectMapper mapper = new ObjectMapper();

		this.mockMvc.perform(post("/planet/")
				.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(PlanetToCreate)))
                .andExpect(status().isPreconditionFailed());
	}
	
	@Test
	public void notCreatePlanetByClimate() throws Exception {

		Planet PlanetToCreate = new Planet();
		PlanetToCreate.setName("Alderaan");
		PlanetToCreate.setTerrain("grasslands, mountains");

		ObjectMapper mapper = new ObjectMapper();

		this.mockMvc.perform(post("/planet/")
				.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(PlanetToCreate)))
                .andExpect(status().isPreconditionFailed());
	}
	
	@Test
	public void notCreatePlanetByName() throws Exception {

		Planet PlanetToCreate = new Planet();
		PlanetToCreate.setClimate("temperate");
		PlanetToCreate.setTerrain("grasslands, mountains");

		ObjectMapper mapper = new ObjectMapper();

		this.mockMvc.perform(post("/planet/")
				.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(PlanetToCreate)))
                .andExpect(status().isPreconditionFailed());
	}
	
	@Test
	public void updatePlanet() throws Exception {

		Planet db_planet = new Planet();
		db_planet.setId(0L);
		db_planet.setName("Alderaan");
		db_planet.setClimate("temperate");
		db_planet.setTerrain("grasslands, mountains");
		planetRepository.save(db_planet);
		
		Planet planetToUpdate = new Planet();
		planetToUpdate.setName("AlderaanUpdated");
		planetToUpdate.setClimate("temperateUpdated");
		planetToUpdate.setTerrain("grasslands, mountains");
		
		ObjectMapper mapper = new ObjectMapper();
		
		this.mockMvc.perform(put("/planet/id/0")
				.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(planetToUpdate)))
                .andExpect(status().isOk());
		
		planetToUpdate = new Planet();
		planetToUpdate.setName("AlderaanUpdated");
		planetToUpdate.setClimate("temperateUpdated");
		
		this.mockMvc.perform(put("/planet/id/0")
				.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(planetToUpdate)))
                .andExpect(status().isPreconditionFailed());
		
		planetToUpdate = new Planet();
		planetToUpdate.setName("AlderaanUpdated");
		planetToUpdate.setTerrain("grasslands, mountains");
		
		this.mockMvc.perform(put("/planet/id/0")
				.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(planetToUpdate)))
                .andExpect(status().isPreconditionFailed());
	}
}