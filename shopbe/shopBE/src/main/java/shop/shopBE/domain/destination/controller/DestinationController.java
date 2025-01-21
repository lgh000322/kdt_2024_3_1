package shop.shopBE.domain.destination.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.request.AddDestinationRequest;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;
import shop.shopBE.domain.destination.service.DestinationFadeService;
import shop.shopBE.domain.destination.service.DestinationService;
import shop.shopBE.global.config.security.mapper.token.AuthToken;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "배송지", description = "배송지 관련 API")
public class DestinationController {

    private final DestinationService destinationService;
    private final DestinationFadeService destinationFadeService;

    // 특정 회원의 배송지 목록 조회
    @GetMapping("/destination/search")
    @Operation(summary = "배송지 조회", description = "현재 로그인한 회원의 배송지 목록을 조회")
    public ResponseEntity<List<Destination>> findDestinationList(
            @AuthenticationPrincipal AuthToken authToken) {

        // 현재 로그인한 사용자의 ID를 가져와 배송지 목록 조회
        List<Destination> destinationList = destinationFadeService.findDestinationList(authToken.getId());
        System.out.println("배송지 조회");
        return ResponseEntity.ok().body(destinationList);
    }

    // 특정 회원의 배송지 추가
    @GetMapping("/destination/add")
    @Operation(summary = "배송지 추가", description = "현재 로그인한 회원의 배송지 목록에 배송지를 추가")
    public void addDestinationList(
            @RequestBody @Valid AddDestinationRequest addDestinationResponse,
            @AuthenticationPrincipal AuthToken authToken) {

        //입력받은 배송지와 사용자 ID를 통해 배송지 추가
        destinationService.addDestinationList(authToken.getId(), addDestinationResponse);
        System.out.println("배송지 추가");
    }

    @GetMapping("/destination/update")
    @Operation(summary="배송지 수정",description = "회원의 배송지 수정")
    public void updateDestinationList(
            @RequestBody @Valid UpdateDestinationRequest UpdateDestinationRequest,
            @AuthenticationPrincipal AuthToken authToken
    ){
        destinationFadeService.updateDestination(authToken.getId(), UpdateDestinationRequest);
        System.out.println("배송지 수정");
    }


    @GetMapping("/destination/delete")
    @Operation(summary="배송지 삭제",description = "회원의 배송지 삭제")
    public void DelDestinationList(
            @RequestBody @Valid UpdateDestinationRequest delDestinationResponse,
            @AuthenticationPrincipal AuthToken authToken
    ){
        destinationService.deleteDestination(delDestinationResponse.id());
        System.out.println("배송지 삭제");
    }
}
