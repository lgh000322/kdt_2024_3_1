package shop.shopBE.global.config.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.utils.cookie.CookieUtils;
import shop.shopBE.global.utils.cookie.data.Token;
import shop.shopBE.global.utils.jwt.JwtUtils;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthToken authToken = (AuthToken) authentication.getPrincipal();

        String accessToken = jwtUtils.createAccessToken(authToken.getSub(), authToken.getRoles());

        String refreshToken = jwtUtils.createRefreshToken(authToken.getSub(), authToken.getRoles());

        ResponseCookie accessTokenCookie = cookieUtils.createCookie(new Token("accessToken", accessToken), "localhost");
        ResponseCookie refreshTokenCookie = cookieUtils.createCookie(new Token("refreshToken", refreshToken), "localhost");

        response.addHeader("Set-Cookie",accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

        String isAuthenticated = authToken.isAuthenticated() ? "true" : "false";

        response.sendRedirect("http://localhost:3000/login/success?isAuthenticated=" + isAuthenticated);
    }
}
