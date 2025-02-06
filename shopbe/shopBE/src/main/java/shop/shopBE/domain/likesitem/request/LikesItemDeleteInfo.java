package shop.shopBE.domain.likesitem.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LikesItemDeleteInfo(
        @NotNull @NotEmpty Long productId

) {
}
