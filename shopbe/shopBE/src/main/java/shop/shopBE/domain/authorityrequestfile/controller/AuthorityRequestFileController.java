package shop.shopBE.domain.authorityrequestfile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shop.shopBE.domain.authorityrequestfile.request.AuthFileData;
import shop.shopBE.domain.authorityrequestfile.service.AuthorityRequestFileFacadeService;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "파일 조회", description = "권한 파일을 조회하는 api")
public class AuthorityRequestFileController {

    private final AuthorityRequestFileFacadeService authorityRequestFileFacadeService;

    @GetMapping("/authority/file/{authorityId}")
    @Operation(summary = "권한 신청 파일 조회", description = "권한 신청 파일을 조회하는 api")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseFormat<List<AuthFileData>>> getAuthFiles(@PathVariable(name = "authorityId") Long auhtorityId) {
        List<AuthFileData> files = authorityRequestFileFacadeService.getFiles(auhtorityId);
        return ResponseEntity.ok().body(ResponseFormat.of("파일 조회에 성공했습니다.", files));
    }
}
