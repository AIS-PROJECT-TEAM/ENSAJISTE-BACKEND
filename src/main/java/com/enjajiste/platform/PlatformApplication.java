package com.enjajiste.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}

	public Docket apis() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.enjajiste")).build();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * @Bean CommandLineRunner run(UserService userService){ return args -> {
	 * userService.saveRole(new Role(null,"Admin")); userService.saveRole(new
	 * Role(null,"Student")); userService.saveRole(new Role(null,"User"));
	 * 
	 * User user = new User(); user.setCne("F134159115"); user.setNom("Chahboune");
	 * user.setPrenom("Ibrahim"); user.setEmail("ibrahimchahboune@gmail.com");
	 * user.setPassword("password"); user.setCin("WA216515");
	 * user.setGender("male"); userService.saveUser(user);
	 * 
	 * User user1 = new User(); user1.setCne("F134159116");
	 * user1.setNom("Chahboune2"); user1.setPrenom("Ibrahim");
	 * user1.setEmail("ibrahimofficiel20@gmail.com"); user1.setPassword("password");
	 * user1.setCin("WA164169"); user1.setGender("female");
	 * userService.saveUser(user1);
	 * 
	 * 
	 * 
	 * userService.addRoleToUser("F134159115", "Admin");
	 * userService.addRoleToUser("F134159116", "Student"); }; }
	 */

}
