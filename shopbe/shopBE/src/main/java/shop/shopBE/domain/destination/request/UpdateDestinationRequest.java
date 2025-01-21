package shop.shopBE.domain.destination.request;

public record UpdateDestinationRequest(

        Long id,

        String destinationName, //배송지 이름

        String receiverName, //받는사람

        String tel, //전화번호

        String address, //주소

        Long zipCode, //우편번호

        boolean isSelectedDestination, //기본 배송지 여부

        String deliverMessage //배송 메시지
) {
}
