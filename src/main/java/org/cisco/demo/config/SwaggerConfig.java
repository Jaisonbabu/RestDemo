package org.cisco.demo.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket demoAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()            
				.apis(RequestHandlerSelectors.basePackage("org.cisco.demo.controllers"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
	     return new ApiInfo("Spring Boot Demo API", "Demo API", "Version 1", "Terms of Service", 
	    		 new Contact("Jaison Babu", "www.cisco.com", "jaison.babu@cisco.com"), "License", "API License URL",  new ArrayList<VendorExtension>());
	}


}
