package shop.shopBE.domain.likesitem.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record LikesPaging(
        @Min(value = 1)
        @NotEmpty
        int page,

        @Min(value = 10)
        @NotEmpty
        int offset
) {
}
