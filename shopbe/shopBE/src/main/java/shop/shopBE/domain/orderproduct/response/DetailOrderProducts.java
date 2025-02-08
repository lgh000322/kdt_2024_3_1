package shop.shopBE.domain.orderproduct.response;

import shop.shopBE.domain.orderproduct.entity.request.OrderProductDeliveryInfo;

public record DetailOrderProducts(
        long orderProductId, //주문상품 ID
        OrderProductDeliveryInfo orderProductDeliveryInfo, //배송상태
        String mainProductName,// 대표 주문 상품
        String mainImageUrl, // 대표 이미지 주소
        int count, //구매 갯수
        int totalPrice //총 가격
) {
}
