package shop.shopBE.global.refresh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.shopBE.global.refresh.service.RefreshService;
import shop.shopBE.global.response.ResponseFormat;

@RestController
@RequiredArgsConstructor
@Tag(name = "리프래시 토큰 관리", description = "리프래시 토큰 관리 API")
public class RefreshController {

    private final RefreshService refreshService;

    @GetMapping("/refresh")
    @Operation(summary = "리프래시 토큰으로 토큰 초기화", description = "리프래시 토큰으로 엑세스 토큰과 리프래시 토큰을 재발급한다.")
    public ResponseEntity<ResponseFormat<Void>> refreshTokens(HttpServletRequest request, HttpServletResponse response) {
        refreshService.getTokens(request, response);
        return ResponseEntity.ok().body(ResponseFormat.of("토큰 재발급 성공"));
    }
}
