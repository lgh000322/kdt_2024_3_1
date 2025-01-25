package shop.shopBE.domain.orderproduct.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderProductRequest(
        @NotNull @NotEmpty Long productId, //상품의 기본키

        @NotNull @NotEmpty int productCount, // 주문 상품의 수

        @NotNull @NotEmpty int productTotalPrice // 주문 상품의 총 가격
) {
}
