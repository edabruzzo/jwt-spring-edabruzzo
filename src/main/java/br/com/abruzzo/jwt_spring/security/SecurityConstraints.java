package br.com.abruzzo.jwt_spring.security;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SecurityConstraints {


    private SecurityConstraints() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    public static final String SECRET = UUID.randomUUID().toString();
    public static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(10);
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_TOKEN = "Authorization";
    public static final String LOGIN_URL = "/login";
    public static final String STATUS_URL = "/status";

}
