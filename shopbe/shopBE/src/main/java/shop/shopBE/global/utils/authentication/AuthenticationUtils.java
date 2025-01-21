package shop.shopBE.global.utils.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.global.config.security.mapper.token.AuthToken;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationUtils {

    public void makeAuthToken(Member member) {
        AuthToken token = AuthToken.createToken(member.getId(), member.getName(), member.getUsername(), member.getRole());
        Authentication authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Authentication getAuthentication(AuthToken authToken) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authToken.getAuthorities());
        return new UsernamePasswordAuthenticationToken(authToken, "", grantedAuthorities);
    }
}
