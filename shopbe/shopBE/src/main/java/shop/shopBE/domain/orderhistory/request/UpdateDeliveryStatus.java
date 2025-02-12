package shop.shopBE.domain.orderhistory.request;

import jakarta.validation.constraints.NotNull;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;

public record UpdateDeliveryStatus(
        @NotNull
        DeliveryStatus deliveryStatus
) {
}
