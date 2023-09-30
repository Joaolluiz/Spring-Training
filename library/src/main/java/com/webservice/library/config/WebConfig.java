package com.webservice.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Diz ao SpringBoot que quando estiver buildando a aplicação, ele precisa ler essa classe, pois nela há configs sobre o comportamento da aplicação
public class WebConfig implements WebMvcConfigurer {

	// Via QUERY PARAM. http://localhost:8080/api/person/v1?mediaType=xml
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		configurer.favorParameter(true)
				.parameterName("mediaType")
				.ignoreAcceptHeader(true)
				.useRegisteredExtensionsOnly(false).defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML);
	}
	
	
	
	
}
