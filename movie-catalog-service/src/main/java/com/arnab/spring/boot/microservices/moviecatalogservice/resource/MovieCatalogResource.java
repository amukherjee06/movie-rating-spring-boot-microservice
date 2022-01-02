package com.arnab.spring.boot.microservices.moviecatalogservice.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.arnab.spring.boot.microservices.moviecatalogservice.models.CatalogItems;
import com.arnab.spring.boot.microservices.moviecatalogservice.models.Movie;
import com.arnab.spring.boot.microservices.moviecatalogservice.models.Rating;
import com.arnab.spring.boot.microservices.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/{userId}")
	public List<CatalogItems> getCatalog(@PathVariable("userId")String userId){
		
		UserRating ratings=restTemplate.getForObject("http://ratings-data-service/ratingsdata/user" +userId, UserRating.class);
		
		return ratings.getUserRating().stream().map(rating-> {
			Movie movie=restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItems(movie.getName(), "Test", rating.getRating());
		}).collect(Collectors.toList());
		
	}

}
