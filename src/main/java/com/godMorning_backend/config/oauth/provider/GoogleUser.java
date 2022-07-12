package com.godMorning_backend.config.oauth.provider;

import java.util.Map;

public class GoogleUser implements OAuthUserInfo{
    private Map<String, Object> attribute;

    //GoogleUser를 통해 map형 attribute를 건네 받고
    public GoogleUser(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    //건네받은 attribute에서 각 항목(id, 메일, 이름)을 찾는 메서드
    @Override
    public String getProviderId() {
        return (String)attribute.get("googleId");
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String)attribute.get("email");
    }

    @Override
    public String getName() {
        return (String)attribute.get("name");
    }
}
