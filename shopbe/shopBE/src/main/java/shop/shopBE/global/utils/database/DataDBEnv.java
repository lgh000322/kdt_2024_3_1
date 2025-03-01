package shop.shopBE.global.utils.database;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource-data")
public record DataDBEnv(
        String url,
        String username,
        String password,
        String driverClassName
) {
}
