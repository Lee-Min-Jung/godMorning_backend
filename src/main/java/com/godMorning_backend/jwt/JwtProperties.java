package com.godMorning_backend.jwt;

public interface JwtProperties {
    String SECRET = "겟인데어";
    int ACCESS_EXPIRATION_TIME = 864000000;
    int REFRESH_EXPIRATION_TIME = 1000000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String REFRESH_HEADER_STRING = "Refresh_Authorization";
}
