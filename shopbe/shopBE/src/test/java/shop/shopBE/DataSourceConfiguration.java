package shop.shopBE;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
public record DataSourceConfiguration(
        String url,
        String username,
        String password,
        String driverClassName
) {
}
