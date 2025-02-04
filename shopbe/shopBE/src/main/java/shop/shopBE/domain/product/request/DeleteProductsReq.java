package shop.shopBE.domain.product.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DeleteProductsReq(
        @NotNull
        List<Long> productIds
) {
}
