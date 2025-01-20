package shop.shopBE.global.config.security.mapper.oauth2;

import java.util.Map;

public class NaverUserMapper implements UserMapper {

    private Map<String, Object> attribute;

    public NaverUserMapper(Map<String,Object> attribute){
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
