package com.arnab.spring.boot.microservices.moviecatalogservice.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.arnab.spring.boot.microservices.moviecatalogservice.models.CatalogItems;
import com.arnab.spring.boot.microservices.moviecatalogservice.models.Movie;
import com.arnab.spring.boot.microservices.moviecatalogservice.models.UserRating;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger=LoggerFactory.getLogger(MovieCatalogResource.class);
	
	@GetMapping("/{userId}")
	@Retry(name="movie-catalog-service",fallbackMethod="fallBackMovieMethod")
	public List<CatalogItems> getCatalog(@PathVariable("userId")String userId){
		
		logger.info("Movie Catalog Service Call");
		UserRating ratings=restTemplate.getForObject("http://ratings-data-service/ratingsdata/user" +userId, UserRating.class);
		
		return 
				
				ratings.getUserRating()
		.stream()
		.map(
			rating-> {
				Movie movie=restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
				return new CatalogItems(movie.getName(), movie.getDescription(), rating.getRating());
		})
		.collect(Collectors.toList());
		
	}
	
	public List<CatalogItems> fallBackMovieMethod(Exception ex) {
		return Arrays.asList(new CatalogItems("No Movie", "Please try again after some time!!!", 0));
	}

}
