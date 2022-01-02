package com.arnab.spring.boot.microservices.ratingsdataservice.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arnab.spring.boot.microservices.ratingsdataservice.resource.models.Rating;
import com.arnab.spring.boot.microservices.ratingsdataservice.resource.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {
	
	UserRating userRating=new UserRating();
	
	@GetMapping("/{movieId}")
	public UserRating getRating(@PathVariable("movieId")String movieId) {
		
		List<Rating> ratings= Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 5)
				);
		
		userRating.setUserRating(ratings);
		return userRating;
	}

}
