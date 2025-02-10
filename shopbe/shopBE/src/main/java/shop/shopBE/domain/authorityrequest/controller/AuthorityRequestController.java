package shop.shopBE.domain.authorityrequest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.authorityrequest.request.AuthorityRequestModel;
import shop.shopBE.domain.authorityrequest.response.AuthorityResponseListModel;
import shop.shopBE.domain.authorityrequest.service.AuthorityRequestFacadeService;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "권한", description = "권한 부여, 권환 요청 목록 확인 API")
public class AuthorityRequestController {

    private final AuthorityRequestFacadeService authorityRequestFacadeService;

    // 일반 회원의 권한 요청
    @PostMapping(value = "/authority", consumes = {"multipart/form-data"})
    @Operation(summary = "회원의 판매자 권한 신청", description = "현재 로그인 한 회원이 파일데이터와 등록 사유를 보내 판매자 권한 상승 요청을 보낸다.")
    public ResponseEntity<ResponseFormat<Void>> makeAuthorityRequest(@RequestPart(name = "file") List<MultipartFile> files,
                                                                     @RequestPart(name = "data") @Valid AuthorityRequestModel authorityRequestModel,
                                                                     @AuthenticationPrincipal AuthToken authToken) {
        authorityRequestFacadeService.makeAuthorityRequest(files, authorityRequestModel, authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("판매자 권한 요청 성공."));
    }

    // 관리자의 권한 허가
    @PutMapping("/authority/{authorityId}")
    @Operation(summary = "판매자 권한 허가", description = "관리자 권한을 가진 회원이 요청을 보낸 회원의 권한을 판매자로 업데이트한다.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseFormat<Void>> updateOtherMemberRole(@PathVariable(name = "authorityId") Long authorityId) {
        authorityRequestFacadeService.updateAuthority(authorityId);
        return ResponseEntity.ok().body(ResponseFormat.of("판매자 권한으로 상승시키는데 성공했습니다."));
    }

    @GetMapping("/authority")
    @Operation(summary = "권한 허가 신청 목록", description = "관리자만 권한 허가 신청한 사람들의 목록을 확인할 수 있다.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseFormat<List<AuthorityResponseListModel>>> getAuthorityRequests(@PageableDefault Pageable pageable,
                                                                                                 @RequestParam(name = "name") String name) {

        List<AuthorityResponseListModel> result = authorityRequestFacadeService.findAuthorityRequests(pageable,name);
        return ResponseEntity.ok().body(ResponseFormat.of("성공", result));
    }

    @DeleteMapping("/authority/{authorityId}")
    @Operation(summary = "판매자 권한 거절", description = "관리자 권한을 가진 회원이 요청을 보낸 회원의 권한을 판매자로 업데이트 하지 않는다.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseFormat<?>> deleteAuthority(@PathVariable(name = "authorityId") Long authorityId) {
        authorityRequestFacadeService.deleteById(authorityId);
        return ResponseEntity.ok().body(ResponseFormat.of("권한 거절에 성공했습니다."));
    }
}
