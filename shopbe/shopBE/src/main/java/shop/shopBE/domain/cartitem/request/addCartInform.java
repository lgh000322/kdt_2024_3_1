package shop.shopBE.domain.cartitem.request;

import jakarta.validation.constraints.NotNull;

public record addCartInform(
        @NotNull
        Long productId,
        @NotNull
        int quantity,
        @NotNull
        int size
) {
}
