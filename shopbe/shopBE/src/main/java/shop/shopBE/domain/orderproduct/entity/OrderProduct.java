package shop.shopBE.domain.orderproduct.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.productdetail.entity.ProductDetail;

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

    @Enumerated(EnumType.STRING)
    private DeliveryStatus currentDeliveryStatus; //배송상태 기록

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_history_id")
    private OrderHistory orderHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    public static OrderProduct createDefaultOrderProduct(int productCount, int productTotalPrice, DeliveryStatus deliveryStatus, OrderHistory orderHistory, ProductDetail productDetail) {
        return OrderProduct.builder()
                .productCount(productCount)
                .productTotalPrice(productTotalPrice)
                .currentDeliveryStatus(deliveryStatus)
                .orderHistory(orderHistory)
                .productDetail(productDetail)
                .build();
    }

    public void changeDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.currentDeliveryStatus = deliveryStatus;
    }

    public void deleteOrderHistory() {
        this.orderHistory = null;
    }
}
