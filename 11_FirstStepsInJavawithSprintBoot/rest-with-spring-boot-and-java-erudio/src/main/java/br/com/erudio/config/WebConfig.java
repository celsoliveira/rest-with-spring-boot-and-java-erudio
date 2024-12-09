package br.com.erudio.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.erudio.serialization.converter.YamlJackson2HttpMesageConverter;

@Configuration 
public class WebConfig implements WebMvcConfigurer {
	
	public static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

	// https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
	
	// Via EXTENSION (EX: http://localhost:8080/person/v1.xml DEPRECATED since 2.6 on SpringBoot
	
	// via QUERY PARAM (http://localhost:8080/person/v1?mediaType=xml)
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMesageConverter());
		
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		
		// Via QUERY PARAM
		/*
		configurer.favorParameter(true); // aceita parametros
		 
		configurer.parameterName("mediaType")							// nome do parametro
				  .ignoreAcceptHeader(true)								// ignora parametros no header
				  .useRegisteredExtensionsOnly(false)					// 	
				  .defaultContentType(MediaType.APPLICATION_JSON)		// ContentType Default
				  .mediaType("json", MediaType.APPLICATION_JSON)		// ContentType suportados (json)
				  .mediaType("xml", MediaType.APPLICATION_XML);			// ContentType suportados (xml)
		 */
		
		 // Via HEADER PARAM
		configurer.favorParameter(false); // não aceita parametros
		
		configurer.ignoreAcceptHeader(false)							// NÂO ignora parametros no header
				  .useRegisteredExtensionsOnly(false)					// 	
				  .defaultContentType(MediaType.APPLICATION_JSON)		// ContentType Default
				  .mediaType("json", MediaType.APPLICATION_JSON)		// ContentType suportados (json)
				  .mediaType("xml", MediaType.APPLICATION_XML)			// ContentType suportados (xml)
				  .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
	}

}
