package repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import document.Planet;
@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {
	Optional<Planet> findByName(String name);
	Optional<Planet> findById(Long id);
	int deleteById(Long id);
}
