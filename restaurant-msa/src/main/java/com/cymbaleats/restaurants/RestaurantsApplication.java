package com.cymbaleats.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import com.google.cloud.spring.data.spanner.repository.config.EnableSpannerRepositories;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class RestaurantsApplication {

	@Autowired
	private Environment env;
	@Value ("${spring.cloud.gcp.spanner.instance-id}")
	private static String test ;
	public static void main(String[] args) {
		System.out.println("RestaurantsController2222 === "+ test);
		new SpringApplicationBuilder().sources(RestaurantsApplication.class).run( args);

	}

}
