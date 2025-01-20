package shop.shopBE.global.config.security.mapper.oauth2;

import java.util.Map;

public class GoogleUserMapper implements UserMapper{

    private Map<String,Object> attribute;

    public GoogleUserMapper(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return  attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
