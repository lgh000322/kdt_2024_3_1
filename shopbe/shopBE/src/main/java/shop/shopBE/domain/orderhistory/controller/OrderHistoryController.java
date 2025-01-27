package shop.shopBE.domain.orderhistory.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.shopBE.domain.orderhistory.request.OrderHistoryInfo;
import shop.shopBE.domain.orderhistory.request.OrderRequest;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderhistory.service.OrderHistoryFadeService;
import shop.shopBE.domain.orderhistory.service.OrderHistoryService;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문내역", description = "주문내역 관련 API")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;
    private final OrderHistoryFadeService orderHistoryFadeService;

    @GetMapping("/order/history")
    @Operation(summary = "주문내역 조회", description = "현재 로그인한 회원의 주문내역들을 조회")
    public ResponseEntity<ResponseFormat<List<OrderHistoryResponse>>> findOrderHistoryList(@AuthenticationPrincipal AuthToken authToken, // 현재 로그인중인 회원의 정보가 담겨있는 객체
                                                                                           @RequestBody @Valid OrderHistoryInfo orderHistoryInfo) {
        //회원 ID로 주문내역들을 리스트들을 가져와 반환
        List<OrderHistoryResponse> orderHistoryList = orderHistoryFadeService.findOrderHistoryList(authToken.getId(), orderHistoryInfo);
        return ResponseEntity.ok().body(ResponseFormat.of("주문내역리스트 조회.", orderHistoryList));
    }

    @PostMapping("/order/history")
    @Operation(summary = "상품 주문", description = "상품주문시 주문내역으로 추가")
    public ResponseEntity<ResponseFormat<Void>> addOrderHistory(@AuthenticationPrincipal AuthToken authToken, @RequestBody @Valid OrderRequest orderRequest) {
        orderHistoryService.orderItems(authToken.getId(), orderRequest);
        return ResponseEntity.ok().body(ResponseFormat.of("상품 주문에 성공했습니다."));
    }

    @DeleteMapping("/order/history/{orderHistoryId}")
    @Operation(summary = "주문내역 삭제", description = "선택한 주문내역을 삭제")
    public ResponseEntity<ResponseFormat<Void>> deleteOrderHistory(@PathVariable(name = "orderHistoryId") Long orderHistoryId) {
        orderHistoryService.deleteOrderHistoryByHistoryId(orderHistoryId);
        return ResponseEntity.ok().body(ResponseFormat.of("주문내역 삭제에 성공했습니다."));
    }

}
