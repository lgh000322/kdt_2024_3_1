package shop.shopBE.domain.orderhistory.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderHistoryResponse(
        Long orderId, // 주문번호
        LocalDateTime createdAt, //주문 일시
        String imageUrl, // 주문상품의 대표이미지
        String content, // ex: 상품 이름 외 n개
        int price // 총 가격
) {
}
