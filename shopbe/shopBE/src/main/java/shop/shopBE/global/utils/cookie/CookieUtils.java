package shop.shopBE.global.utils.cookie;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import shop.shopBE.global.utils.cookie.data.CookieData;
import shop.shopBE.global.utils.jwt.property.JwtProperties;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CookieUtils {

    private final JwtProperties jwtProperties;


    public <T extends CookieData> ResponseCookie createCookie(T cookieData, String domainAdr) {

        boolean isHttpOnly;
        long maxAge;

        if (cookieData.getKey().equals("accessToken")) {
            // accessToken인 경우에 자바스크립트로 가져올 수 있게 함.
            isHttpOnly = false;
            maxAge= jwtProperties.accessTokenExpiration();
        } else if(cookieData.getKey().equals("refreshToken")){
            // accessToken이 아닌 refreshToken일 경우에 자바스크립트로 가져갈 수 없게 함.
            isHttpOnly = true;
            maxAge = jwtProperties.refreshTokenExpiration();
        }else{
            isHttpOnly=false;
            maxAge = 0;
        }

        return ResponseCookie
                .from(cookieData.getKey(), cookieData.getValue())
                .httpOnly(isHttpOnly)
                .maxAge((maxAge/1000)) // 초로 변환
                .domain(domainAdr)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();
    }

    public Optional<Cookie> getCookies(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        Cookie resultCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    resultCookie = cookie;
                    break;
                }
            }
        }

        return Optional.ofNullable(resultCookie);
    }

    public Cookie deleteRefreshCookie() {
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setAttribute("SameSite", "None");
        refreshTokenCookie.setMaxAge(0);

        return refreshTokenCookie;
    }


}
