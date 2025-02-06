package shop.shopBE.domain.orderproduct.entity.request;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;

import java.time.LocalDateTime;

@Embeddable
@Builder
public record OrderProductDeliveryInfo(
    LocalDateTime changedAt,
    DeliveryStatus deliveryStatus
    ){
        public static OrderProductDeliveryInfo createDefaultOrderProductDeliveryEntity(DeliveryStatus state, LocalDateTime time){
            return OrderProductDeliveryInfo.builder()
                    .changedAt(time)
                    .deliveryStatus(state)
                    .build();
        }
}
