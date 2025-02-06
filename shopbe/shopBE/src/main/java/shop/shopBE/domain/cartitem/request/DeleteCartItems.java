package shop.shopBE.domain.cartitem.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DeleteCartItems(
        @NotNull
        List<Long> cartItemIds
) {
}
