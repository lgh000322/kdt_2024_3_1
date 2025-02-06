package shop.shopBE.domain.orderproduct.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;
import shop.shopBE.domain.orderproduct.entity.request.OrderProductDeliveryInfo;
import shop.shopBE.domain.product.entity.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int productCount;

    private int productTotalPrice;

    private DeliveryStatus currentDeliveryStatus; //배송상태 기록

    private LocalDateTime changedAt; //배송 현재 날짜

    //배송상태 기록
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "orderProductDeliveryInfo", joinColumns = @JoinColumn(name = "orderProductId"))
    private List<OrderProductDeliveryInfo> deliveryStatusHistory = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_history_id")
    private OrderHistory orderHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void updateDeliveryStatus(OrderProductDeliveryInfo newDeliveryInfo) {
        this.currentDeliveryStatus = newDeliveryInfo.deliveryStatus(); // 배송 상태 업데이트
        this.changedAt = newDeliveryInfo.changedAt(); // 변경된 시간 저장

        deliveryStatusHistory.add(newDeliveryInfo);
    }

}
