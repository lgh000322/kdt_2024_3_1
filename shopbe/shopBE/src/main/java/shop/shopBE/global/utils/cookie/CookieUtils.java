package shop.shopBE.global.utils.cookie;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import shop.shopBE.global.utils.cookie.data.CookieData;

import java.util.Optional;

@Component
public class CookieUtils {

    public <T extends CookieData> Cookie createCookie(T cookieData) {
        Cookie cookie = new Cookie(cookieData.getKey(), cookieData.getValue());

        if (cookieData.getKey().equals("accessToken")) {
            // accessToken인 경우에 자바스크립트로 가져올 수 있게 함.
            cookie.setHttpOnly(false);
        } else {
            // accessToken이 아닌 refreshToken일 경우에 자바스크립트로 가져갈 수 없게 함.
            cookie.setHttpOnly(true);
        }
        cookie.setDomain("kdt2024-3-1.vercel.app");  // 실제 도메인으로 변경 필요
        cookie.setSecure(true);
        cookie.setMaxAge(cookie.getMaxAge());
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "None");

        return cookie;
    }

    public Optional<Cookie> getCookies(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        Cookie resultCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    resultCookie=cookie;
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
        refreshTokenCookie.setAttribute("SameSite","None");
        refreshTokenCookie.setMaxAge(0);

        return refreshTokenCookie;
    }


}
