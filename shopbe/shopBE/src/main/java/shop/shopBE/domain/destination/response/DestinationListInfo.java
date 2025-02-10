package shop.shopBE.domain.destination.response;

/*
배송지 응답 Dto
 */
public record DestinationListInfo(
        Long destinationId,
        String destinationName, // 배송지 이름
        String receiverName,    // 수령인
        String tel,             // 전화번호
        String address,         // 주소
        Long zipCode,           // 우편번호
        String deliveryMessage,  // 배송 메시지
        boolean isSelectedDefault //기본 배송지 여부
) {
}
