package shop.shopBE.global.utils.database;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource-meta")
public record MetaDBEnv(
        String url,
        String username,
        String password,
        String driverClassName
) {
}
