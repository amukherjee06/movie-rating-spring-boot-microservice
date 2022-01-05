package com.arnab.spring.boot.microservices.springbootconfigdemo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arnab.spring.boot.microservices.springbootconfigdemo.config.DbDetails;

/**
 * 
 * Various ways of using @Value annotations in Spring
 *
 */

@RestController
@RefreshScope
public class SpringBootConfigDemoController {
	
	//If it doesn't fine my.greeting,the class will still load and pick up
	//"some default value"
	@Value("${my.greeting: some default value}")
	private String greetingValue;
	
	@Value("${my.list.values}")
	private List<String> listOfValues;
	
	@Value("some static message")
	private String staticMessage;
	
	@Value("#{${db.connectionString}}")
	private Map<String,String> dbValueMap;
	
	@Autowired
	private DbDetails dbDetails;
	
	/**
	 * This is used for looking up profiles and properties
	 * This should be avoided as much as possible
	 */
	@Autowired
	private Environment environment;
	
	@GetMapping("/config-demo")
	public String getConfig() {
		return greetingValue;
	}
	
	@GetMapping("/config-demo-list")
	public List<String> getConfigList() {
		return listOfValues;
	}

	@GetMapping("/config-demo-static")
	public String getStaticMessage() {
		return staticMessage;
	}
	
	@GetMapping("/config-demo-map")
	public Map<String,String> getConfigMap() {
		return dbValueMap;
	}
	
	@GetMapping("/config-demo-bean")
	public String getConfigBean() {
		return dbDetails.getConnectionString()+" "+dbDetails.getHost()+" "+dbDetails.getPort();
	}
	
	@GetMapping("/config-demo-envDetails")
	public String getEnvironmentDetails() {
		return environment.toString();
	}
}
