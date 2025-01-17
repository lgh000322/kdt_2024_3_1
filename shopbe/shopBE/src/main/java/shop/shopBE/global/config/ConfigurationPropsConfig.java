package shop.shopBE.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import shop.shopBE.global.utils.JwtProperties;

@Configuration
@EnableConfigurationProperties(value = JwtProperties.class)
public class ConfigurationPropsConfig {
}