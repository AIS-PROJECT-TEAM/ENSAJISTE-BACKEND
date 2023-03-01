package com.enjajiste.platform.utils;

import com.auth0.jwt.algorithms.Algorithm;

public class Constants {

    private static final String SECRET = "secret";
    public static final Algorithm MY_ALGORITHM = Algorithm.HMAC256(SECRET.getBytes());
    public static final int ACCESS_TOKEN_EXPIRED_AT = 10 * 60 * 1000;
    public static final int REFRESH_TOKEN_EXPIRED_AT = 30 * 60 * 1000;
    public static final String CLIENT_URL = "http://localhost:3000";

}
