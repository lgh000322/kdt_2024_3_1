package shop.shopBE.global.utils.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.exception.MemberExceptionCode;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.filter.exception.JwtExceptionCode;
import shop.shopBE.global.utils.jwt.property.JwtProperties;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {
    private Key key;
    private static final String MEMBER_ROLE = "role";

    private final JwtProperties jwtProperties;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(UUID memberSub, List<String> roles) {
        long now = (new Date()).getTime();

        // Access token 유효 기간 설정
        Date accessValidity = new Date(now + jwtProperties.accessTokenExpiration());

        return Jwts.builder()
                .setIssuedAt(new Date(now)) // jwt 발급 시간
                .setExpiration(accessValidity) // jwt 만료 시간
                .setIssuer(jwtProperties.issuer()) // jwt 발급한 주체 (서버의 이름)
                .setSubject(memberSub.toString()) // 토큰의 주체.
                .addClaims(Map.of(MEMBER_ROLE, roles)) // jwt 의 추가적인 정보
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // JWT 헤더
                .signWith(key, SignatureAlgorithm.HS512) // Signature 방법
                .compact();
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(UUID memberSub, List<String> roles) {
        long now = (new Date()).getTime();

        // Refresh token 유효 기간 설정
        Date refreshValidity = new Date(now + jwtProperties.refreshTokenExpiration());

        // Refresh token 생성
        return  Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(refreshValidity)
                .setIssuer(jwtProperties.issuer())
                .setSubject(memberSub.toString())
                .addClaims(Map.of(MEMBER_ROLE, roles))
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public void validateToken(final String token) {
        try {
            log.info("Current date: {}", new Date());

            // 토큰 파싱 및 검증
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token);

            // 토큰의 만료일 확인 (파싱 과정에서 유효성 검증이 자동으로 수행됨)
            Date expiration = claims.getBody().getExpiration();
            log.info("Token expiration date: {}", expiration);
        } catch (ExpiredJwtException e) {
            throw new CustomException(JwtExceptionCode.JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(JwtExceptionCode.JWT_NOT_SUPPORTED);
        } catch (MalformedJwtException e) {
            throw new CustomException(JwtExceptionCode.JWT_WRONG_FORM);
        } catch (JwtException e) {
            throw new CustomException(JwtExceptionCode.JWT_NOT_VALID);
        }
    }

    public Member getMember(String token) {
        String sub = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();

        UUID uuid = UUID.fromString(sub);  // String을 UUID로 변환
        log.info("in getMember() sub: {}", uuid);

        return memberRepository.findBySub(uuid)  // UUID로 회원 조회
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));
    }

}
