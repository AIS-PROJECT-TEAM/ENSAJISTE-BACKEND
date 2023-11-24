package com.enjajiste.platform;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.enjajiste.platform.models.Role;
import com.enjajiste.platform.models.User;
import com.enjajiste.platform.services.UserService;

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

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
//			userService.saveRole(new Role(1, "Admin"));
//			userService.saveRole(new Role(2, "Student"));
//			userService.saveRole(new Role(3, "User"));
//
//			Role role = new Role();
//			role.setName("Admin");
//			userService.saveRole(role);
//
//			Role role1 = new Role();
//			role1.setName("Student");
//			userService.saveRole(role1);
//
//			Role role2 = new Role();
//			role2.setName("User");
//			userService.saveRole(role2);
//
//			User user = new User();
//			user.setCne("F134159115");
//			user.setNom("Chahboune");
//			user.setPrenom("Ibrahim");
//			user.setEmail("ibrahimchahboune@gmail.com");
//			user.setPassword("password");
//			user.setCin("WA216515");
//			user.setGender("male");
//			userService.saveUser(user);
//
//			User user1 = new User();
//			user1.setCne("F134159116");
//			user1.setNom("Chahboune2");
//			user1.setPrenom("Ibrahim");
//			user1.setEmail("ibrahimofficiel20@gmail.com");
//			user1.setPassword("password");
//			user1.setCin("WA164169");
//			user1.setGender("female");
//			userService.saveUser(user1);
//
//			User user2 = new User();
//			user2.setCne("R148016319");
//			user2.setNom("Akhmim");
//			user2.setPrenom("Abdelilah");
//			user2.setEmail("akhmim.abdelilah@gmail.com");
//			user2.setPassword("pswd");
//			user2.setCin("BA15322");
//			user2.setGender("male");
//			userService.saveUser(user2);
//
//			userService.addRoleToUser("F134159115", "Admin");
//			userService.addRoleToUser("R148016319", "Student");
		};
	}

}
