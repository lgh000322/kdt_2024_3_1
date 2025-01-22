package shop.shopBE.global.filter;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.filter.exception.JwtExceptionCode;
import shop.shopBE.global.utils.authentication.AuthenticationUtils;
import shop.shopBE.global.utils.jwt.JwtUtils;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtUtils jwtUtils;
    private final AuthenticationUtils authenticationUtils;

    private static final String[] WHITE_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/*",
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        for (String path : WHITE_LIST) {
            if (request.getRequestURI().startsWith(path)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request)
                .orElseThrow(() -> new CustomException(JwtExceptionCode.JWT_NOT_FOUND));

        jwtUtils.validateToken(token);

        Member member = jwtUtils.getMember(token);

        authenticationUtils.makeAuthToken(member);
        filterChain.doFilter(request, response);
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(token) && token.startsWith("bearer ")) {
            return Optional.of(token.substring(7));
        }

        return Optional.empty();
    }
}
