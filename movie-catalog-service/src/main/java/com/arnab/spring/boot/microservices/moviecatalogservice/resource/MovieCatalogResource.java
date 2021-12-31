package com.arnab.spring.boot.microservices.moviecatalogservice.resource;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arnab.spring.boot.microservices.moviecatalogservice.models.CatalogItems;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@GetMapping("/{userId}")
	public List<CatalogItems> getCatalog(@PathVariable("userId")String userId){
		
		return Collections.singletonList(
				
				new CatalogItems("Transformers", "Test", 4)
				
				);
		
	}

}
