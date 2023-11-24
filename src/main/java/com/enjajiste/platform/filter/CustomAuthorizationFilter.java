package com.enjajiste.platform.filter;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enjajiste.platform.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil = new JWTUtil();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")
				|| request.getServletPath().equals("/api/logout") || request.getServletPath().equals("/api/file/upload")
				|| request.getServletPath().equals("/api/register")) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

				try {
					String token = authorizationHeader.substring("Bearer ".length());
//                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//                    JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = jwtUtil.decodeJWTToken(token);
					String email = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					stream(roles).forEach(role -> {
						authorities.add(new SimpleGrantedAuthority(role));
					});
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							email, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (Exception exception) {
					log.error("Error loging in :  {}", exception.getMessage());
					response.setHeader("error", exception.getMessage());
					response.setStatus(FORBIDDEN.value());
					// response.sendError(HttpServletResponse.SC_FORBIDDEN);
					Map<String, String> error = new HashMap<>();
					error.put("error_message", exception.getMessage());
					error.put("token_type", "access_token");
					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			} else {
				filterChain.doFilter(request, response);
			}

		}
	}
}
