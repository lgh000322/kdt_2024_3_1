package shop.shopBE.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    // CORS 설정을 HttpSecurity에 적용하기 위한 Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 서로 다른 도메인 간 쿠키 전달 허용
        config.setAllowedOrigins(List.of("https://api.fmanshop.com", "http://localhost:3000"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Cache-Control"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 메서드, OPTIONS는 pre-flight 요청을 위해 허용해야 함.
        config.setMaxAge(3600L); // 최대 캐시 시간

        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 설정
        return source;
    }
}