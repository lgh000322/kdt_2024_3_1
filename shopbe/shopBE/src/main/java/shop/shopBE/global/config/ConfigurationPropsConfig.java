package shop.shopBE.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import shop.shopBE.global.utils.database.DataDBEnv;
import shop.shopBE.global.utils.database.MetaDBEnv;
import shop.shopBE.global.utils.jwt.property.JwtProperties;

@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class, DataDBEnv.class, MetaDBEnv.class})
public class ConfigurationPropsConfig {
}