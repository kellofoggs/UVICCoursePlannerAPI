package dev.kellyo.UVicCoursePlanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class UVicCoursePlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UVicCoursePlannerApplication.class, args);
	}

	@GetMapping("/root")
	public String apiRoot(){
		return "HELLO WORLD!";
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v1/courses").allowedOrigins("http://127.0.0.1:5501/");
				registry.addMapping("/api/v1/courses/getPreReqs").allowedOrigins("http://127.0.0.1:5501/");
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
			}
		};
	}

}
