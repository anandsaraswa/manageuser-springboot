package com.usermanagement.boot;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import antlr.collections.List;

@SpringBootApplication
public class UsermanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementApplication.class, args);
	}
	
	  /**
     * Cross-domain configuration
     */
    @Bean
    public CorsFilter corsFilter()
    {
    	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowCredentials(true);
          config.addAllowedOriginPattern("*");
          config.addAllowedHeader("*");
          config.addAllowedMethod("OPTIONS");
          config.addAllowedMethod("GET");
          config.addAllowedMethod("POST");
          config.addAllowedMethod("PUT");
          config.addAllowedMethod("DELETE");
          source.registerCorsConfiguration("/**", config);
          return new CorsFilter(source);
    }

}
