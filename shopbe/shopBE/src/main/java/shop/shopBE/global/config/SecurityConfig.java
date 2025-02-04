package shop.shopBE.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.shopBE.global.config.security.handler.CustomSuccessHandler;
import shop.shopBE.global.config.security.handler.JwtAccessDeniedHandler;
import shop.shopBE.global.config.security.entry.JwtAuthenticationEntryPoint;
import shop.shopBE.global.config.security.service.CustomOauth2Service;
import shop.shopBE.global.filter.JwtAuthenticationFilter;
import shop.shopBE.global.filter.JwtResponseFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomOauth2Service customOauth2Service;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtResponseFilter jwtResponseFilter;


    private final String[] WHITE_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/banners/**",
            "/JWT/**",
            "/member/logout"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP 기본 인증을 비활성화
                .cors(Customizer.withDefaults()) // CORS 활성화 - corsConfigurationSource 이름의 빈 사용
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능 비활성화
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 필터를 UsernamePasswordAuthenticationFilter 전에 추가
                .addFilterBefore(jwtResponseFilter, JwtAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증되지 않은 사용자가 보호된 리소스에 액세스 할 때 호출
                            .accessDeniedHandler(jwtAccessDeniedHandler); //권한이 없는 사용자가 보호된 리소스에 액세스 할 때 호출
                })
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOauth2Service))
                        .successHandler(customSuccessHandler)
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   // JWT 사용해서 세션 사용 X
                .authorizeHttpRequests(auth -> auth // 요청에 대한 인증 설정
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated());  //이외의 요청은 전부 인증 필요

        return http.build();
    }
}
