package shop.shopBE.global.utils.authentication;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthenticationUtils {

    public void makeAuthToken(Member member) {
        AuthToken token = AuthToken.createToken(member.getId(), member.getName(), member.getUsername(), member.getRole(), member.isAuthenticated(),member.getSub());
        log.info("token의 role={}", token.getRoles());
        Authentication authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Authentication getAuthentication(AuthToken authToken) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authToken.getAuthorities());
        return new UsernamePasswordAuthenticationToken(authToken, "", grantedAuthorities);
    }
}
