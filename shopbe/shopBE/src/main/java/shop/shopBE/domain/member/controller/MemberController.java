package shop.shopBE.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.shopBE.domain.member.request.MemberUpdateInfo;
import shop.shopBE.domain.member.response.MemberInformation;
import shop.shopBE.domain.member.service.MemberFacadeService;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 관련", description = "회원 관련 API")
public class MemberController {

    private final MemberFacadeService memberFacadeService;

    @PutMapping("/member")
    @Operation(summary = "회원 수정", description = "현재 로그인 한 회원의 정보를 수정한다.")
    public ResponseEntity<ResponseFormat<Void>> updateMemberInformation(@RequestBody @Valid MemberUpdateInfo memberUpdateInfo,
                                                                        @AuthenticationPrincipal AuthToken authToken) {
        memberFacadeService.updateMember(authToken.getId(), memberUpdateInfo);
        return ResponseEntity.ok().body(ResponseFormat.of("회원 정보 업데이트 성공"));
    }

    @GetMapping("/member")
    @Operation(summary = "회원 조회", description = "현재 로그인 한 회원의 전체 정보를 조회한다.")
    public ResponseEntity<ResponseFormat<MemberInformation>> getMemberInformation(@AuthenticationPrincipal AuthToken authToken) {

        MemberInformation memberInformation = memberFacadeService.findMemberInfoById(authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("회원 조회 성공", memberInformation));
    }

}
