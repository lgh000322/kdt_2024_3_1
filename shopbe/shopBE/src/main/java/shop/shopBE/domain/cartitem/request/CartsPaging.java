package shop.shopBE.domain.cartitem.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record CartsPaging(
        @Min(value = 1)
        @NotEmpty
        int page,

        @Min(value = 10)
        @NotEmpty
        int size
) {
}
