package shop.shopBE.global.refresh.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.refresh.exception.RefreshExceptionCode;
import shop.shopBE.global.utils.cookie.CookieUtils;
import shop.shopBE.global.utils.cookie.data.Token;
import shop.shopBE.global.utils.jwt.JwtUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshService {

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    public void getTokens(HttpServletRequest request,HttpServletResponse response) {
        String refreshToken = cookieUtils.getCookies(request, "refreshToken")
                .orElseThrow(() -> new CustomException(RefreshExceptionCode.REFRESH_TOKEN_NOT_FOUND)).getValue();

        // 유효성 검사
        jwtUtils.validateToken(refreshToken);

        Member member = jwtUtils.getMember(refreshToken);

        String accessTokenReissued = jwtUtils.createAccessToken(member.getSub(), List.of(member.getRole().name()));
        String refreshTokenReissued = jwtUtils.createRefreshToken(member.getSub(), List.of(member.getRole().name()));


        ResponseCookie accessTokenCookie = cookieUtils.createCookie(new Token("accessToken", accessTokenReissued), "kdt2024-3-1.vercel.app");
        ResponseCookie refreshTokenCookie = cookieUtils.createCookie(new Token("refreshToken", refreshTokenReissued), "kdt2024-3-1.vercel.app");

        response.addHeader("Set-Cookie",accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
    }
}
