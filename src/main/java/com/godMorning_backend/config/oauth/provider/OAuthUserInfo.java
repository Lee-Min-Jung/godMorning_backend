package com.godMorning_backend.config.oauth.provider;

public interface OAuthUserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
