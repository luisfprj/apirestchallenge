package controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import service.PlanetService;
import document.Planet;
import repository.PlanetRepository;

@RestController
@RequestMapping("/planet")
public class PlanetController {
	
	@Autowired
	private PlanetRepository planetRepository;
	
	private PlanetService planetService;

	PlanetController(PlanetRepository planetRepository) {
		this.planetRepository = planetRepository;
		this.planetService = new PlanetService();
	}
	
	@GetMapping
	public Collection<Planet> getAllPlanets() {
		return this.planetRepository.findAll();
	}
	
	@GetMapping("/id/{id}")
	public @ResponseBody ResponseEntity<Planet> getPlanetById(@PathVariable Long id) {
		Optional<Planet> planet = this.planetRepository.findById(id);
		
		if (planet.isPresent()) {
			return new ResponseEntity<Planet>(planet.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Planet>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/name/{name}")
	public @ResponseBody ResponseEntity<Planet> getPlanetByName(@PathVariable String name) {

		Optional<Planet> planet = this.planetRepository.findByName(StringUtils.capitalize(name.toLowerCase()));
		if (planet.isPresent()) {
			return  new ResponseEntity<Planet>(planet.get(), HttpStatus.OK);
		} else {
			return  new ResponseEntity<Planet>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public @ResponseBody ResponseEntity<Planet> addPlanet(@RequestBody Planet planet) {
		
		if (planet.getClimate() == null || planet.getTerrain() == null || planet.getName() == null) {
			return new ResponseEntity<Planet>(HttpStatus.PRECONDITION_FAILED);
		}
		if (this.planetRepository.findByName(planet.getName()).isPresent()) {
			return new ResponseEntity<Planet>(HttpStatus.CONFLICT);
		}
		
		Optional<Planet> lastPlanet = this.planetRepository.findAll().stream().max(Comparator.comparingLong(Planet::getId));
		planet.setId(lastPlanet.isPresent() ? lastPlanet.get().getId()+1 : 0);
		
		planet.setCreated(LocalDate.now());		

		int planetMovieCount = this.planetService.getPlanetMovieCount(planet.getName(), null);
		planet.setFilms(planetMovieCount);
		
		return new ResponseEntity<Planet>(this.planetRepository.save(planet), HttpStatus.CREATED);
	}
	
	@PutMapping("/id/{id}")
	public  @ResponseBody ResponseEntity<Planet> editPlanet(@RequestBody Planet planet, @PathVariable Long id) {
		Optional<Planet> planetSource = this.planetRepository.findById(id);
		
		if (planetSource.isPresent()) {
			
			if (planet.getClimate() == null || planet.getTerrain() == null || planet.getName() == null) {
				return new ResponseEntity<Planet>(HttpStatus.PRECONDITION_FAILED);
			}
			if (this.planetRepository.findByName(planet.getName()).isPresent()) {
				return new ResponseEntity<Planet>(HttpStatus.CONFLICT);
			}
			
			planet.setId(planetSource.get().getId());
			planet.setCreated(planetSource.get().getCreated());
			
			if (planetSource.get().getName().equals(planet.getName())) {
				planet.setFilms(planetSource.get().getFilms());
			} else {
				int planetMovieCount = this.planetService.getPlanetMovieCount(planet.getName(), null);
				planet.setFilms(planetMovieCount);
			}
			planet.setEdited(LocalDate.now());
			return new ResponseEntity<Planet>(this.planetRepository.save(planet), HttpStatus.OK);
		} else {
			return new ResponseEntity<Planet>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/id/{id}")
	public  @ResponseBody ResponseEntity<String> deletePlanetById(@PathVariable Long id) {

		if (this.planetRepository.deleteById(id) > 0) {
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
}
