package shop.shopBE.domain.orderhistory.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import shop.shopBE.domain.orderproduct.request.OrderProductRequest;

import java.util.List;

public record OrderRequest(
        @NotNull @Min(0) int totalPrice, // 주문 내역의 최종 가격

//        @NotNull Long destinationId, // 회원의 배송지 기본키
        
        String address, //주소
        
        String destinationName, //배송지 이름

        String receiverName, //받는 사람

        String tel, //전화번호
        
        Long zipCode, //우편번호

        String deliveryMessage, //배송메시지

        @NotEmpty @NotNull List<OrderProductRequest> orderProductRequests // 주문한 상품리스트 정보
) {
}