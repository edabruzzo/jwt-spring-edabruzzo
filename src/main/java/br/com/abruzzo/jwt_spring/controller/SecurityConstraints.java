package br.com.abruzzo.jwt_spring.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SecurityConstraints {

    public static final String SECRET = UUID.randomUUID().toString();
    public static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(10);
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_TOKEN = "Authorizatin";
    public static final String LOGIN_URL = "/login";
    public static final String STATUS_URL = "/status";






}
