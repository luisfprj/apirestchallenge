package service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

class PlanetServiceResponse {
	public ArrayList<PlanetDTO> results;
	public String next;
} 

class PlanetDTO {
	public String name;
	public String url;
	public ArrayList<String> films;
}

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetService {
	String planetApiUrl = "https://swapi.co/api/planets";

	public PlanetServiceResponse getPlanets (String url) {
		
		if(url == null) {
			url = planetApiUrl;
		}
		
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpComponentsClientHttpRequestFactory  rf = new HttpComponentsClientHttpRequestFactory ();
		rf.setHttpClient(httpClient);

		RestTemplate restTemplate = new RestTemplate(rf);
		
		PlanetServiceResponse planets = restTemplate.getForObject(url, PlanetServiceResponse.class);
		return planets;
	}
	
	public int getPlanetMovieCount(String planetName, String apiUrl) {
		PlanetServiceResponse planetResult = getPlanets(apiUrl);
		Optional<PlanetDTO> planet = planetResult.results.stream().filter(o -> o.name.equals(planetName)).findFirst();
		
		if(planet.isPresent()) {
			return planet.get().films.size();
		}
		
		if(planetResult.next == null) {
			return 0;
		}
		
		return getPlanetMovieCount(planetName, planetResult.next);
	}
}
