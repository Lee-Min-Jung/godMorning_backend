package com.godMorning_backend.config.jwt;

public interface JwtProperties {
    String SECRET = "겟인데어";
    int EXPIRATION_TIME = 864000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
