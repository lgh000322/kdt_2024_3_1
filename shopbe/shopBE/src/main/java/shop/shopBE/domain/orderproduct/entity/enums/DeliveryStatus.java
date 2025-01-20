package shop.shopBE.domain.orderproduct.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliveryStatus {
    BEFORE_DELIVERY("배송 전"),
    START_DELIVERY("배송 중"),
    END_DELIVERY("배송 후");

    private final String description;
}
