package shop.shopBE.domain.cartitem.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemInform(
        @NotNull
        Long productId,   // 프로덕트아이디

        @Min(1)
        int quantity,  //장바구니에 추가한 상품 수량

        @Min(10)
        int size,     //사이즈

        @Min(1)
        int itemPrice  //장바구니에 추가한 상품 가격(수량 x 상품가격)
) {
}
