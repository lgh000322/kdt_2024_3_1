package shop.shopBE.global.utils.cookie;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;
import shop.shopBE.global.utils.cookie.data.CookieData;

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

        cookie.setSecure(true);
        cookie.setMaxAge(cookie.getMaxAge());
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }


}
