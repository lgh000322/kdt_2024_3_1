package shop.shopBE.domain.orderhistory.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import shop.shopBE.domain.orderproduct.request.OrderProductRequest;

import java.util.List;

public record OrderRequest(
        @Min(0)
        int totalPrice, // 주문 내역의 최종 가격

        @NotNull
        @NotEmpty
        String address, //주소

        @NotNull
        @NotEmpty
        String destinationName, //배송지 이름

        @NotNull
        @NotEmpty
        String receiverName, //받는 사람

        @NotNull
        @NotEmpty
        String tel, //전화번호

        @NotNull
        Long zipCode, //우편번호

        @NotNull
        @NotEmpty
        String deliveryMessage, //배송메시지

        int orderProductCount, // 주문한 종 제품 종류의 수

        @NotEmpty
        @NotNull
        List<OrderProductRequest> orderProductRequests // 주문한 상품리스트 정보
) {
}