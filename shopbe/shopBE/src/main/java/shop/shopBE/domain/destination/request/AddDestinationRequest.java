package shop.shopBE.domain.destination.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

//배송지 추가 DTO
public record AddDestinationRequest(
        @NotNull
        @NotEmpty
        String destinationName, //배송지 이름

        @NotNull
        @NotEmpty
        String receiverName, //받는사람

        @NotNull
        @NotEmpty
        String tel, //전화번호

        @NotNull
        @NotEmpty
        String address, //주소

        @NotNull
        @NotEmpty
        Long zipCode, //우편번호

        boolean isSelectedDestination // 기본 배송지 여부
) {
}
