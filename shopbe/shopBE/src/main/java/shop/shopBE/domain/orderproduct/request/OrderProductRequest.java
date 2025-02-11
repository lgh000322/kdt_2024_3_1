package shop.shopBE.domain.orderproduct.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderProductRequest(
        @NotNull
        Long productId, //상품의 기본키

        int shoesSize, // 주문한 신발의 사이즈

        @Min(1)
        int productCount, // 주문 상품의 수

        @Min(0)
        int productTotalPrice // 주문 상품의 총 가격
) {
}