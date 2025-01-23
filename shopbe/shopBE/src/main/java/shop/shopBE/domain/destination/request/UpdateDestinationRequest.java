package shop.shopBE.domain.destination.request;

import lombok.Builder;
import shop.shopBE.domain.destination.response.DestinationListInfo;

@Builder
public record UpdateDestinationRequest(

        Long id,

        String destinationName, // 배송지 이름

        String receiverName, // 받는 사람

        String tel, // 전화번호

        String address, // 주소

        Long zipCode, // 우편번호

        boolean isSelectedDestination, // 기본 배송지 여부

        String deliverMessage // 배송 메시지
) {
    // 정적 메서드: 기존 DestinationListInfo 객체로 UpdateDestinationRequest 생성
    public static UpdateDestinationRequest DestinationUpdate(
            Long id, //배송메시지는 유지될것임.
            String destinationName, // 배송지 이름
            String receiverName, // 받는 사람
            String tel, // 전화번호
            String address, // 주소
            Long zipCode, // 우편번호
            boolean isSelectedDestination, // 기본 배송지 여부
            String deliverMessage){// 배송 메시지

        return UpdateDestinationRequest.builder()
                .id(id)
                .destinationName(destinationName)
                .receiverName(receiverName)
                .tel(tel)
                .address(address)
                .zipCode(zipCode)
                .isSelectedDestination(isSelectedDestination)
                .deliverMessage(deliverMessage)
                .build();
    }
}
