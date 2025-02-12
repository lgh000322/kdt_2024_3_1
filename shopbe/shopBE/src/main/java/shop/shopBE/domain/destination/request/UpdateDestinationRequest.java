package shop.shopBE.domain.destination.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import shop.shopBE.domain.destination.response.DestinationListInfo;

@Builder
public record UpdateDestinationRequest(

        @NotEmpty
        @NotNull
        String destinationName, // 배송지 이름

        @NotEmpty
        @NotNull
        String receiverName, // 받는 사람

        @NotEmpty
        @NotNull
        String tel, // 전화번호

        @NotEmpty
        @NotNull
        String address, // 주소

        @NotNull
        Long zipCode, // 우편번호

        boolean isSelectedDestination, // 기본 배송지 여부

        String deliverMessage // 배송 메시지
) {
}
