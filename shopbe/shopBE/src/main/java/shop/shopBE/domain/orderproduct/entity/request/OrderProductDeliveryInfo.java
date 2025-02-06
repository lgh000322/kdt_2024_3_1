package shop.shopBE.domain.orderproduct.entity.request;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Builder
public class OrderProductDeliveryInfo {
    LocalDateTime changedAt;
    DeliveryStatus deliveryStatus;


    public static OrderProductDeliveryInfo createDefaultOrderProductDeliveryEntity(DeliveryStatus state){
        return OrderProductDeliveryInfo.builder()
                .changedAt(LocalDateTime.now())
                .deliveryStatus(state)
                .build();
    }
}
