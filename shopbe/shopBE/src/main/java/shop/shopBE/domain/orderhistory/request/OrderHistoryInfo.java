package shop.shopBE.domain.orderhistory.request;

import lombok.Builder;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;

import java.time.LocalDateTime;

@Builder
public record OrderHistoryInfo(
        int orderPrice, // 총 주문 가격
        LocalDateTime createdAt, // 주문 날짜
        Long destinationId, // 배송지 ID (엔티티 직접 참조 X)
        Long memberId // 주문자 ID (엔티티 직접 참조 X)
) {
    public static OrderHistoryInfo fromEntity(OrderHistory orderHistory) {
        return OrderHistoryInfo.builder()
                .orderPrice(orderHistory.getOrderPrice())
                .createdAt(orderHistory.getCreatedAt())
                .destinationId(orderHistory.getDestination().getId())
                .memberId(orderHistory.getMember().getId())
                .build();
    }
}
