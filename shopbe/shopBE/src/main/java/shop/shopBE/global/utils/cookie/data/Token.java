package shop.shopBE.global.utils.cookie.data;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Token implements CookieData {
    private final String key;
    private final String token;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return token;
    }
}
