package shop.shopBE.domain.product.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record ProductPaging (

    @Min(value = 1)
    int page,
    @Max(value = 10)
    int size
){
}
