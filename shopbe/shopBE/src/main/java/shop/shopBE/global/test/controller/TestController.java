package shop.shopBE.global.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.exception.MemberExceptionCode;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.response.ResponseFormat;
import shop.shopBE.global.utils.jwt.JwtUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "JWT 발급", description = "테스트 JWT 발급 API")
public class TestController {

    private final JwtUtils jwtUtils;
    private final MemberRepository memberRepository;

    @GetMapping("/JWT/test")
    @Operation(summary = "JWT 발급", description = "회원의 accessToken을 발급한다.")
    public ResponseEntity<ResponseFormat<String>> getAccessToken(@RequestParam(name = "memberId") Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));

        Role role = member.getRole();
        List<String> list = new ArrayList<>();
        list.add(role.getRoleDescription());
        String accessToken = jwtUtils.createAccessToken(member.getSub(), list);

        return ResponseEntity.ok().body(ResponseFormat.of("테스트 JWT 생성에 성공했습니다.", accessToken));
    }
}
