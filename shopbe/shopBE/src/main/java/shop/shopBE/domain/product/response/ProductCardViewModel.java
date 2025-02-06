package shop.shopBE.domain.product.response;

import jakarta.validation.constraints.NotNull;

// 메인페이지 상품리스트 DTO
public record ProductCardViewModel(
        @NotNull
        Long prouductId,
        @NotNull
        String imgUrl,
        @NotNull
        String productName,
        @NotNull
        int price
) {
}
