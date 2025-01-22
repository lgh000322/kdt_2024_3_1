package shop.shopBE.global.utils.cookie;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;
import shop.shopBE.global.utils.cookie.data.CookieData;

@Component
public class CookieUtils {

    public <T extends CookieData> Cookie createCookie(T cookieData) {
        Cookie cookie = new Cookie(cookieData.getKey(), cookieData.getValue());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(cookie.getMaxAge());
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }
}
