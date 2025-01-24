package shop.shopBE.domain.orderhistory.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.request.OrderHistoryInfo;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderhistory.service.OrderHistoryFadeService;
import shop.shopBE.domain.orderhistory.service.OrderHistoryService;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문내역", description = "주문내역 관련 API")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;
    private final OrderHistoryFadeService orderHistoryFadeService;

    @GetMapping("/orderHistory")
    @Operation(summary="주문내역 조회", description = "현재 로그인한 회원의 주문내역들을 조회")
    public ResponseEntity<ResponseFormat<List<OrderHistoryResponse>>> findOrderHistoryList(
            @RequestBody @Valid OrderHistory orderHistory,
            @AuthenticationPrincipal AuthToken authToken){

        //회원 ID로 주문내역들을 리스트들을 가져와 반환
        List<OrderHistoryResponse> orderHistoryList=orderHistoryFadeService.findOrderHistoryList(authToken.getId());
        
        return ResponseEntity.ok().body(ResponseFormat.of("주문내역리스트 조회.", orderHistoryList));
    }

    @PostMapping("/orderHistory")
    @Operation(summary="주문내역 추가", description = "상품주문시 주문내역으로 추가")
    public void addOrderHistory(
            @RequestBody @Valid OrderProduct orderProduct,
            @RequestBody @Valid Destination destination,
            @AuthenticationPrincipal AuthToken authToken){
        orderHistoryService.addOrderHistory(authToken.getId(),orderProduct,destination);
    }

    @DeleteMapping("/orderHistory/{orderHistoryId}")
    @Operation(summary="주문내역 삭제", description = "선택한 주문내역을 삭제")
    public void deleteOrderHistory( @PathVariable(name = "orderHistoryId") Long orderHistoryId){
        orderHistoryService.deleteOrderHistoryByHistoryId(orderHistoryId);
    }
    
}
