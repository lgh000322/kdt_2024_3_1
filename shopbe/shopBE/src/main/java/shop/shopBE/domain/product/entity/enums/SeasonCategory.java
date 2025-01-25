package shop.shopBE.domain.product.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SeasonCategory {
    WINTER("겨울"),
    SUMMER("여름"),
    ALL_SEASON("모든시즌");

    private final String description;
}
