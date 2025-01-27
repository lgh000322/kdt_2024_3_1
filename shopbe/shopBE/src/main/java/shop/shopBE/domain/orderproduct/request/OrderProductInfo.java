package shop.shopBE.domain.orderproduct.request;

import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;
import shop.shopBE.domain.product.entity.Product;

import java.time.LocalDateTime;

public record OrderProductInfo(
        Long orderHistoryId, //주문내역 주문번호
        int productCount, //구매상품 수량
        int productTotalPrice, //총 구매 가격
        DeliveryStatus deliveryState, //배송 상태
        LocalDateTime changedAt,
        Product product
) {
}
