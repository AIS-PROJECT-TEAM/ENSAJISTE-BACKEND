package com.enjajiste.platform.filter;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enjajiste.platform.models.Role;
import com.enjajiste.platform.models.User;
import com.enjajiste.platform.services.UserService;
import com.enjajiste.platform.utils.JWTUtil;
import com.enjajiste.platform.utils.UserDetailsCustomResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:8080/", allowedHeaders = "http")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserControler {

	private final UserService userService;
	private final JWTUtil jwtUtil;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok().body(userService.getUsers());
	}

	@GetMapping("/test")
	public String test() {
		return "Hello";
	}

	@PostMapping("/user/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}

	@PostMapping("/role/save")
	public ResponseEntity<Role> saveUser(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}

	@PostMapping("/role/addtouser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
		userService.addRoleToUser(form.getCne(), form.getRoleName());
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final ArrayList<String> authorizationHeader = new ArrayList<String>();
		log.info("runned");
		if (request.getCookies() != null) {
			stream(request.getCookies()).forEach(cookie -> {
				if (cookie.getName().equals("refresh_token")) {
					authorizationHeader.add(cookie.getValue().toString());
				}
			});
		}
		if (authorizationHeader.size() > 0) {

			try {
				String refresh_token = authorizationHeader.get(0);
				DecodedJWT decodedJWT = jwtUtil.decodeJWTToken(refresh_token);
				String email = decodedJWT.getSubject();
				User user = userService.findByEmail(email);
				String access_token = jwtUtil.createAccessToken(email, request,
						user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

				Map<String, Object> tokens = new HashMap<>();
				tokens.put("error", false);
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception exception) {

				response.setHeader("error", exception.getMessage());
				Map<String, Object> error = new HashMap<>();
				error.put("error", true);
				error.put("error_message", exception.getMessage());
				error.put("token_type", "refresh_token");
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			Map<String, Object> error = new HashMap<>();
			error.put("error", true);
			error.put("error_message", "Referesh token is missing");
			error.put("token_type", "refresh_token");
			response.setContentType(APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), error);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/user/details")
	public UserDetailsCustomResponse getUserDetails(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		log.info("user details runned");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("authenticated user : {}", auth.getPrincipal());
		UserDetailsCustomResponse userDetailsCustomResponse = jwtUtil.getUserFromAccessToken(request);
		response.setStatus(userDetailsCustomResponse.getStatus());
		return userDetailsCustomResponse;

	}

	@PostMapping("/logout")
	public void Logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final ArrayList<String> authorizationHeader = new ArrayList<String>();
		if (request.getCookies() != null) {
			stream(request.getCookies()).forEach(cookie -> {
				if (cookie.getName().equals("refresh_token")) {
					authorizationHeader.add(cookie.getValue().toString());
				}
			});
			if (authorizationHeader.size() > 0) {

				try {
					String refresh_token = authorizationHeader.get(0);
					Cookie cookie = new Cookie("refresh_token", refresh_token);
					cookie.setSecure(true);
					cookie.setHttpOnly(true);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					response.setStatus(OK.value());
				} catch (Exception e) {
					response.setStatus(FORBIDDEN.value());
					Map<String, String> error = new HashMap<>();
					error.put("error_message", e.getMessage());
					error.put("token_type", "refresh_token");
					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			} else {
				response.setStatus(FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", "Refresh token unavailable");
				error.put("token_type", "refresh_token");
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {
			response.setStatus(FORBIDDEN.value());
			Map<String, String> error = new HashMap<>();
			error.put("error_message", "There is no cookie");
			error.put("token_type", "refresh_token");
			response.setContentType(APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), error);
		}
	}

	@PostMapping("/register")
	private Object register(@Validated @RequestBody User user) {
		Map<String, Object> response = new HashMap<>();
		try {
//			User user1 = userService.saveUser(user);
			userService.addRoleToUser(user.getCne(), "User");
			response.put("error", false);
			response.put("message", "usersaved");

		} catch (Exception e) {
			log.error("Error registering the user : {}", e.getMessage());
			response.put("error", true);
			response.put("message", e.getMessage());
			response.put("request", user);
		}
		return response;
	}

}

@Data
class RoleToUserForm {
	private String cne;
	private String roleName;
}
