package shop.shopBE.global.config.security.mapper.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import shop.shopBE.domain.member.entity.enums.Role;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
public class AuthToken implements OAuth2User {
    private final Long id; // 회원의 기본키
    private final String name; // 회원의 이름
    private final String username; // ex) google 1223123123
    private final List<String> roles;// 회원의 역할
    private final UUID sub; // jwt에 들어가는 UUID 값

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public static AuthToken createToken(Long id, String name, String username, Role role) {
        return AuthToken.builder()
                .id(id)
                .name(name)
                .username(username)
                .sub(UUID.randomUUID())
                .roles(List.of(role.getRoleDescription()))
                .build();
    }

}
