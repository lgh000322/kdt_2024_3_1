package shop.shopBE.global.config.security.mapper.oauth2;

public interface UserMapper {

    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();
}
