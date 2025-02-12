package shop.shopBE.domain.orderproduct.request;

import jakarta.validation.constraints.NotNull;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;

public record OrderProductDeliveryInfo(
        @NotNull
        DeliveryStatus deliveryStatus
) {
}
