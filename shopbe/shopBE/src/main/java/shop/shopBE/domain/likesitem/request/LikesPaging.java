package shop.shopBE.domain.likesitem.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record LikesPaging(
        @Min(value = 1)
        int page,

        @Max(value = 10)
        int size
) {
}
