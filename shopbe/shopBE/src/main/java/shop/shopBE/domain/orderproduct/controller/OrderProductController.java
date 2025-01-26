package shop.shopBE.domain.orderproduct.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderproduct.request.OrderProductInfo;
import shop.shopBE.domain.orderproduct.service.OrderProductService;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "주문상품", description = "주문상품 관련 API")
public class OrderProductController {

    private final OrderProductService orderProductService;

    // Todo
    @GetMapping("/orderProduct")
    @Operation(summary = "주문상품 상세조회", description = "현재 로그인한 회원의 주문상품 상세조회")
    public ResponseEntity<ResponseFormat<List<OrderProductInfo>>> findOrderProduct(@RequestBody @Valid OrderHistory orderHistory) {
        List<OrderProductInfo> findOrderProductlist = orderProductService.findOrderProductByHistoryId(orderHistory.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("주문 상세조회 성공", findOrderProductlist));
    }

}
