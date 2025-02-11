package shop.shopBE.domain.orderproduct.response;


import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;


public record OrderProductInfo(
        Long productId, // 상품의 아이디
        String productName, // 해당 상품의 이름
        String orderName, //주문자 이름
        String deliveryAddress, //주문자 주소
        String phoneNumber, //주문자 전화번호
        DeliveryStatus deliveryStatus, // 현재 주문 상태
        String imgUrl , // 해당 상품의 대표 이미지
        int totalPrice // 해당 상품의 총 가격
) { }
