package shop.shopBE.domain.product.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductStatus {
    ON_SALE("판매 중"), SOLD_OUT("품절");
    private final String description;
}
