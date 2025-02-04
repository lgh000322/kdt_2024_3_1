package shop.shopBE.domain.destination.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.shopBE.domain.destination.request.AddDestinationRequest;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;
import shop.shopBE.domain.destination.response.DestinationListInfo;
import shop.shopBE.domain.destination.service.DestinationFadeService;
import shop.shopBE.domain.destination.service.DestinationService;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "배송지", description = "배송지 관련 API")
@Slf4j
public class DestinationController {

    private final DestinationService destinationService;
    private final DestinationFadeService destinationFadeService;

    // 특정 회원의 배송지 목록 조회
    @GetMapping("/destination")
    @Operation(summary = "배송지 조회", description = "현재 로그인한 회원의 배송지 목록을 조회")
    public ResponseEntity<ResponseFormat<List<DestinationListInfo>>> findDestinationList(@AuthenticationPrincipal AuthToken authToken) {

        // 현재 로그인한 사용자의 ID를 가져와 배송지 목록 조회
        List<DestinationListInfo> destinationList = destinationFadeService.findDestinationList(authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("배송지 조회성공",destinationList));
    }

    // 특정 회원의 배송지 추가
    @PostMapping("/destination")
    @Operation(summary = "배송지 추가", description = "현재 로그인한 회원의 배송지 목록에 배송지를 추가")
    public ResponseEntity<ResponseFormat<Void>> addDestinationList(@RequestBody @Valid AddDestinationRequest addDestinationResponse,
                                                                   @AuthenticationPrincipal AuthToken authToken) {
        //입력받은 배송지와 사용자 ID를 통해 배송지 추가
        destinationService.addDestinationList(authToken.getId(), addDestinationResponse);
        return ResponseEntity.ok().body(ResponseFormat.of("배송지 추가에 성공했습니다."));

    }

    @PutMapping("/destination/{destinationId}")
    @Operation(summary = "배송지 수정", description = "회원의 배송지 수정")
    public ResponseEntity<ResponseFormat<Void>> updateDestinationList(
            @RequestBody @Valid UpdateDestinationRequest updateDestinationRequest,
            @PathVariable(name = "destinationId") Long destinationId
    ) {

        destinationFadeService.updateDestination(updateDestinationRequest,destinationId);
        return ResponseEntity.ok().body(ResponseFormat.of("회원의 배송지 수정에 성공했습니다."));
    }


    @DeleteMapping("/destination/{destinationId}")
    @Operation(summary="배송지 삭제",description = "회원의 배송지 삭제")
    public ResponseEntity<ResponseFormat<Void>> delDestinationList(@PathVariable(name = "destinationId") Long destinationId){
        destinationService.deleteDestination(destinationId);
        return ResponseEntity.ok().body(ResponseFormat.of("회원의 배송지를 삭제하는데 성공했습니다."));
    }
}
