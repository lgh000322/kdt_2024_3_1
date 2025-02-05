package shop.shopBE.global.config.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.global.config.security.exception.OauthExceptionCode;
import shop.shopBE.global.config.security.mapper.oauth2.GoogleUserMapper;
import shop.shopBE.global.config.security.mapper.oauth2.NaverUserMapper;
import shop.shopBE.global.config.security.mapper.oauth2.UserMapper;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOauth2Service extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        UserMapper userMapper = chooseUserMapper(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        String username = userMapper.getProvider() + " " + userMapper.getProviderId();

        Member member = getOrCreateMember(username, userMapper);

        return AuthToken.createToken(member.getId(), member.getName(), member.getUsername(), member.getRole(), member.isAuthenticated(), member.getSub());
    }

    private UserMapper chooseUserMapper(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "naver" -> new NaverUserMapper(attributes);
            case "google" -> new GoogleUserMapper(attributes);
            default -> throw new CustomException(OauthExceptionCode.NOT_SUPPORTED);
        };
    }

    private Member getOrCreateMember(String username, UserMapper userMapper) {
        return memberRepository.findByUsername(username)
                .orElseGet(() -> memberRepository.save(Member.createDefaultMember(username, userMapper.getName(), userMapper.getEmail(), Role.USER, false)));
    }

}
