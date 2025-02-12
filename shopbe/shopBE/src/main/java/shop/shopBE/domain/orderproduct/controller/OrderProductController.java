package shop.shopBE.domain.orderproduct.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.shopBE.domain.orderproduct.request.OrderProductDeliveryInfo;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;
import shop.shopBE.domain.orderproduct.service.OrderProductService;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "주문상품", description = "주문상품 관련 API")
public class OrderProductController {

    private final OrderProductService orderProductService;

    @GetMapping("/orderProduct/{orderHistoryId}")
    @Operation(summary = "주문상품 상세조회", description = "주문내역으로 주문상품 상세조회")
    public ResponseEntity<ResponseFormat<List<OrderProductInfo>>> findOrderProduct(@PathVariable(name = "orderHistoryId") Long orderHistoryId) {
        List<OrderProductInfo> orderInfos = orderProductService.getOrderInfos(orderHistoryId);
        return ResponseEntity.ok().body(ResponseFormat.of("주문 상세조회 성공", orderInfos));
    }

    @PutMapping("/orderProduct/{productDetailId}")
    @Operation(summary = "주문상품 배송상태 업데이트", description = "주문상품 배송상태를 업데이트(배송전, 배송중, 배송후 등으로 변경)")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ResponseFormat<List<OrderProductInfo>>> updateOrderProductDeliveryState(@PathVariable(name = "productDetailId") Long orderProductDetailId,
                                                                                                  @RequestBody @Valid OrderProductDeliveryInfo orderProductDeliveryInfo) {
        orderProductService.updateOrderProductDeliveryState(orderProductDetailId, orderProductDeliveryInfo.deliveryStatus());
        return ResponseEntity.ok().body(ResponseFormat.of("주문 상품 배송상태 업데이트 성공"));
    }

    @DeleteMapping("/orderProduct/{orderProductId}")
    @Operation(summary = "주문 상품 로그 삭제", description = "주문한 상품의 기록을 삭제한다.")
    public ResponseEntity<ResponseFormat<Void>> deleteOrderProduct(@PathVariable(name = "orderProductId") Long orderProductId) {
        orderProductService.deleteByOrderProductId(orderProductId);
        return ResponseEntity.ok().body(ResponseFormat.of("주문 상품 로그를 삭제하는데 성공했습니다."));
    }

}
