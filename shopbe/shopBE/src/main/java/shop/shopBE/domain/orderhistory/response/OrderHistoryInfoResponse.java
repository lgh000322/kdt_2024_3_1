package shop.shopBE.domain.orderhistory.response;


public record OrderHistoryInfoResponse(
        String mainProductName,// 대표 주문 상품
        String mainImageUrl // 대표 이미지 주소
) {
}