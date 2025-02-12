package shop.shopBE.domain.cartitem.request;

import jakarta.validation.constraints.Min;


// 장바구니 아이템 수정 dto
public record UpdateCartItemInform(
        @Min(1)
        int size,         // 상품사이즈
        @Min(1)
        int quantity      // 수량

) {
}
