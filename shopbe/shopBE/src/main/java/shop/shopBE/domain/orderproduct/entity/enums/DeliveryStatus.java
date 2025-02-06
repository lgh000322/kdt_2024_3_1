package shop.shopBE.domain.orderproduct.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliveryStatus {
    BEFORE_DELIVERY("배송 전"),
    START_DELIVERY("배송 중"),
    END_DELIVERY("배송 후"),
    BEFORE_PAY("결제 전"),
    CANCEL_DELIVERY("주문 취소"); // 서버 어플리케이션에서 현재 상태가 배송 전 또는 결제 전 일때만 변경이 가능함

    private final String description;
}
