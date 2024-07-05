package com.tuti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class Daos2024Application {

	public static void main(String[] args) {
		SpringApplication.run(Daos2024Application.class, args);
	}

	  @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("API MECA")
	                        .version("1.0")
	                        .description("Api del Grupo DAOS = Amor - Ceballos - Espindola - Martin")
	                        .termsOfService("https://cmlapazonline.com/politica-de-privacidad/")
	                        .license(new License().name("LICENSE").url("https://tuti.com")));
	  }
	        
	        
}
