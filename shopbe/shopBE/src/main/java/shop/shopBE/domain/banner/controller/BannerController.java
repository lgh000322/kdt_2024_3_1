package shop.shopBE.domain.banner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.banner.request.BannerUpdateRequestForDelete;
import shop.shopBE.domain.banner.response.BannerResponse;
import shop.shopBE.domain.banner.service.BannerFacadeService;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "배너 사진", description = "배너 사진 저장, 배너 사진 조회")
public class BannerController {

    private final BannerFacadeService bannerFacadeService;

    @PostMapping(value = "/banner", consumes = {"multipart/form-data"})
    @Operation(summary = "배너 사진 저장", description = "관리자만 배너 사진을 저장할 수 있다.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseFormat<Void>> saveBanners(@RequestPart(name = "file") List<MultipartFile> files,
                                                            @AuthenticationPrincipal AuthToken authToken) {
        bannerFacadeService.saveBanners(files, authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("배너 사진 저장에 성공했습니다."));
    }

    @GetMapping("/banners")
    @Operation(summary = "배너 사진 조회", description = "모든 사용자는 배너 사진을 조회할 수 있다.")
    public ResponseEntity<ResponseFormat<List<BannerResponse>>> getBanners() {
        List<BannerResponse> banners = bannerFacadeService.getBanners();
        return ResponseEntity.ok().body(ResponseFormat.of("배너 조회 성공", banners));
    }

    @DeleteMapping("/banner/{bannerId}")
    @Operation(summary = "배너 삭제", description = "관리자는 배너 사진을 삭제할 수 있다.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseFormat<Void>> deleteBanner(@PathVariable(name = "bannerId") Long bannerId) {
        bannerFacadeService.deleteById(bannerId);
        return ResponseEntity.ok().body(ResponseFormat.of("배너를 지우는데 성공했습니다."));
    }

    @PutMapping(value = "/banner", consumes = {"multipart/form-data"})
    @Operation(summary = "배너 사진 전체 업데이트", description = "관리자는 현재 등록된 배너 사진을 모두 업데이트 할 수 있다.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseFormat<Void>> updateBanners(@RequestPart(name = "saveFile", required = false) List<MultipartFile> saveFiles,
                                                              @RequestPart(name = "deleteBannerId", required = false) List<BannerUpdateRequestForDelete> bannerUpdateRequestForDeletes,
                                                              @AuthenticationPrincipal AuthToken authToken) {
        bannerFacadeService.updateFiles(saveFiles, bannerUpdateRequestForDeletes, authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("배너 사진 업데이트에 성공했습니다."));
    }
}
