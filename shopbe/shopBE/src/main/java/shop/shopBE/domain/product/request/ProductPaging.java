package shop.shopBE.domain.product.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record ProductPaging (
    @Min(value = 1)
    @NotEmpty
    int page,
    @Max(value = 10)
    @NotEmpty
    int size
){
}
