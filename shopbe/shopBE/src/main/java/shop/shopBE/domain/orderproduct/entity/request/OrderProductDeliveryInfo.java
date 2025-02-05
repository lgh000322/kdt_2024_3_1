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
        public static OrderProductDeliveryInfo createDefaultOrderProductDeliveryEntity(DeliveryStatus state){
            return OrderProductDeliveryInfo.builder()
                    .changedAt(LocalDateTime.now())
                    .deliveryStatus(state)
                    .build();
        }

    public DeliveryStatus getDeliveryStatus() {
            return deliveryStatus;
    }

    public LocalDateTime getChangedAt(){
            return changedAt;
    }
}
