package com.enjajiste.platform.security;

import static com.enjajiste.platform.utils.Constants.CLIENT_URL;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService = null;
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * // http.requiresChannel().anyRequest().requiresSecure();
		 * CustomAuthentificationFilter customAuthentificationFilter = new
		 * CustomAuthentificationFilter(authenticationManagerBean());
		 * customAuthentificationFilter.setFilterProcessesUrl("/api/login");
		 * http.csrf().disable();
		 * http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
		 * STATELESS); // Accessed by all requests
		 * http.cors().and().authorizeRequests().antMatchers("/api/login/**",
		 * "/api/token/refresh/**", "/api/file/upload", "/api/logout",
		 * "/api/register").permitAll(); // Admin Requests
		 * http.authorizeRequests().antMatchers(GET,
		 * "/api/user/**").hasAnyAuthority("Admin", "Student");
		 * http.authorizeRequests().antMatchers(POST,
		 * "/api/documents/**").hasAnyAuthority("Admin", "Student","User");
		 * http.authorizeRequests().antMatchers(DELETE,
		 * "/api/documents/**").hasAnyAuthority("Admin", "Student","User");
		 * http.authorizeRequests().antMatchers(GET,
		 * "/api/documents/**").hasAnyAuthority("Admin", "Student","User"); // User
		 * Requests
		 * 
		 * // Student Request
		 * http.authorizeRequests().antMatchers("/api/user/details").hasAnyAuthority(
		 * "Student"); // Other endpoints that are common between all users
		 * http.authorizeRequests().anyRequest().authenticated(); // Adding the filter
		 * to handle Authentification operation
		 * http.addFilter(customAuthentificationFilter); // Adding filter to scan the
		 * upcoming request and search for the access token http.addFilterBefore(new
		 * CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		 */
		// for testing api :
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/swagger-ui.html", "/api/user/**", "/api/register", "/api/logout", "/api/file/upload",
						"/api/login/**", "/api/user/details", "/api/documents/**", "/api/user/**")
				.permitAll();

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(CLIENT_URL));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowCredentials(true);
		configuration.addAllowedHeader("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
