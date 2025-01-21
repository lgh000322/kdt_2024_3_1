package shop.shopBE.domain.likesitem.request;

import jakarta.validation.constraints.NotNull;

public record LikesItemInfo(
        @NotNull  Long productId
) {
}
