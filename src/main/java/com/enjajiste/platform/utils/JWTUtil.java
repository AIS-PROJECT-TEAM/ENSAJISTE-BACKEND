package com.enjajiste.platform.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enjajiste.platform.models.User;
import com.enjajiste.platform.services.UserService;
import com.enjajiste.platform.services.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enjajiste.platform.utils.Constants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTUtil {

    private  Algorithm algorithm = MY_ALGORITHM;
    @Autowired
    private  UserService userService;

    public String createAccessToken(String subject, HttpServletRequest request, List<Object> authorities){
        return  JWT.create()
                .withSubject(subject)
                .withIssuer(request.getRequestURL().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_AT))
                .withClaim("roles", authorities)
                .sign(algorithm);
    }


    public String createRefreshToken(String subject,HttpServletRequest request){
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_AT))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    public UserDetailsCustomResponse getUserFromAccessToken(HttpServletRequest request) throws IOException {
        UserDetailsCustomResponse userDetailsCustomResponse = new UserDetailsCustomResponse();
        User user = null;
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String email = decodedJWT.getSubject();
                user = userService.findByEmail(email);
                userDetailsCustomResponse.setUser(user);
                userDetailsCustomResponse.setStatus(OK.value());
                userDetailsCustomResponse.setIserror(false);
                userDetailsCustomResponse.setMessage("User found");
            }catch(Exception exception){
                log.error("error : {}", exception.getMessage());
                userDetailsCustomResponse.setStatus(BAD_REQUEST.value());
                userDetailsCustomResponse.setIserror(true);
                userDetailsCustomResponse.setMessage("Access Token is Invalid");
            }
        }else {
            userDetailsCustomResponse.setStatus(FORBIDDEN.value());
            userDetailsCustomResponse.setIserror(true);
            userDetailsCustomResponse.setMessage("Access Token not found");
        }

        return userDetailsCustomResponse;
    }


    public DecodedJWT decodeJWTToken(String token){
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

}
