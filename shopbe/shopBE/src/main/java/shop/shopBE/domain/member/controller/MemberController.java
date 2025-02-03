package shop.shopBE.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.shopBE.domain.member.request.MemberListRequest;
import shop.shopBE.domain.member.request.MemberRoleUpdate;
import shop.shopBE.domain.member.request.MemberUpdateInfo;
import shop.shopBE.domain.member.response.MemberInformation;
import shop.shopBE.domain.member.response.MemberListResponse;
import shop.shopBE.domain.member.response.MemberListResponseView;
import shop.shopBE.domain.member.service.MemberFacadeService;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

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

    //회원의 역할만 수정
    @PutMapping("/member/{memberId}")
    @Operation(summary = "회원의 역할 수정", description = "회원의 아이디를 입력받아 회원의 역할을 수정한다.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseFormat<Void>> updateMemberRole(@RequestBody @Valid MemberRoleUpdate memberRoleUpdate,
                                                                 @PathVariable(name = "memberId") Long memberId) {
        memberFacadeService.updateMemberRole(memberRoleUpdate.role(), memberId);
        return ResponseEntity.ok().body(ResponseFormat.of("회원의 역할을 수정하는데 성공했습니다."));
    }

    //회원의 데이터 수정
    @GetMapping("/member")
    @Operation(summary = "회원 조회", description = "현재 로그인 한 회원의 전체 정보를 조회한다.")
    public ResponseEntity<ResponseFormat<MemberInformation>> getMemberInformation(@AuthenticationPrincipal AuthToken authToken) {
        MemberInformation memberInformation = memberFacadeService.findMemberInfoById(authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("회원 조회 성공", memberInformation));
    }

    @GetMapping("members")
    @Operation(summary = "회원 정보 전체 조회", description = "관리자는 모든 회원의 정보를 조회할 수 있다")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseFormat<List<MemberListResponseView>>> getMembers(@RequestBody @Valid MemberListRequest memberListRequest) {
        List<MemberListResponseView> memberList = memberFacadeService.getMemberList(memberListRequest);
        return ResponseEntity.ok().body(ResponseFormat.of("회원 리스트 조회에 성공했습니다.", memberList));
    }

}
