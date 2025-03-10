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
import shop.shopBE.global.filter.exception.JWTExceptionCode;
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
    public String createAccessToken(String memberSub, List<String> roles) {
        long now = (new Date()).getTime();

        // Access token 유효 기간 설정
        Date accessValidity = new Date(now + jwtProperties.accessTokenExpiration());

        return Jwts.builder()
                .setIssuedAt(new Date(now)) // jwt 발급 시간
                .setExpiration(accessValidity) // jwt 만료 시간
                .setIssuer(jwtProperties.issuer()) // jwt 발급한 주체 (서버의 이름)
                .setSubject(memberSub) // 토큰의 주체.
                .addClaims(Map.of(MEMBER_ROLE, roles)) // jwt 의 추가적인 정보
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // JWT 헤더
                .signWith(key, SignatureAlgorithm.HS512) // Signature 방법
                .compact();
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(String memberSub, List<String> roles) {
        long now = (new Date()).getTime();

        // Refresh token 유효 기간 설정
        Date refreshValidity = new Date(now + jwtProperties.refreshTokenExpiration());

        // Refresh token 생성
        return Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(refreshValidity)
                .setIssuer(jwtProperties.issuer())
                .setSubject(memberSub)
                .addClaims(Map.of(MEMBER_ROLE, roles))
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public void validateToken(final String token) {
        try {
            log.info("now date: {}", new Date());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            claims.getBody().getExpiration();
        } catch (MalformedJwtException e) {
            throw new CustomException(JWTExceptionCode.JWT_INVALID_MALFORMED);
        } catch (ExpiredJwtException e) {
            throw new CustomException(JWTExceptionCode.JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(JWTExceptionCode.JWT_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new CustomException(JWTExceptionCode.JWT_CLAIM_EMPTY);
        } catch (Exception e) {
            throw new CustomException(JWTExceptionCode.JWT_INVALID);
        }
    }

    public Member getMember(String token) {
        String sub = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();

        return memberRepository.findBySub(sub)  // UUID로 회원 조회
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));
    }

}
