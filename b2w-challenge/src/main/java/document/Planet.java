package document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

@Document
public class Planet {
	@Id
	private Long id;
	private String name;
	private String climate;
	private String terrain;
	private String diameter;
	private String gravity;
	private String orbital_period;
	private String population;
	private String[] residents;
	private String rotation_period;
	private String surface_water;
	private String url;
	private int films;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private LocalDate created;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private LocalDate edited;
	
	
	public Planet() {
	}
	
	public Planet(Long id, String name, String climate, String terrain) {
		this.id = id;
		setName(name);		
		setClimate(climate);
		setTerrain(terrain);
		setCreated(LocalDate.now());
	}
	
	@Override
	public String toString() {
		return  String.format("[ name: %s, "
				+ "rotation_period: %s, "
				+ "orbital_period: %s, "
				+ "diameter: %s, "
				+ "climate: %s, "
				+ "gravity: %s, "
				+ "terrain: %s, "
				+ "surface_water: %s, "
				+ "population: %s, "
				+ "residents: %s, "
				+ "films: %s, "
				+ "created: %s, "
				+ "edited: %s, "
				+ "url: %s ]",
				 this.name, this.rotation_period, this.orbital_period, this.diameter, this.climate, this.gravity,
				 this.terrain, this.surface_water, this.population, this.residents, this.films, this.created, this.edited, this.url);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Planet) {
			Planet other = (Planet) o;
			if (other.getName().equals(this.getName())) {
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name != null ? StringUtils.capitalize(name.toLowerCase()): null;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public String getGravity() {
		return gravity;
	}

	public void setGravity(String gravity) {
		this.gravity = gravity;
	}

	public String getOrbital_period() {
		return orbital_period;
	}

	public void setOrbital_period(String orbital_period) {
		this.orbital_period = orbital_period;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String[] getResidents() {
		return residents;
	}

	public void setResidents(String[] residents) {
		this.residents = residents;
	}

	public String getRotation_period() {
		return rotation_period;
	}

	public void setRotation_period(String rotation_period) {
		this.rotation_period = rotation_period;
	}

	public String getSurface_water() {
		return surface_water;
	}

	public void setSurface_water(String surface_water) {
		this.surface_water = surface_water;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getFilms() {
		return films;
	}

	public void setFilms(int films) {
		this.films = films;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public void setEdited(LocalDate edited) {
		this.edited = edited;
	}

	public LocalDate getCreated() {
		return created;
	}

	public LocalDate getEdited() {
		return edited;
	}
	
	
}
