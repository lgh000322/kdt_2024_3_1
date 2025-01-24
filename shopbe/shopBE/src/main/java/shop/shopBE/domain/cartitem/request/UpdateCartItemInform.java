package shop.shopBE.domain.cartitem.request;

import jakarta.validation.constraints.NotNull;


// 장바구니 아이템 수정 dto
public record UpdateCartItemInform(
        @NotNull
        int size,         // 상품사이즈
        @NotNull
        int quantity,      // 수량
        @NotNull
        int itemPrice      // 장바구니 상품 가격(수량 X 상품단가)
) {
}
