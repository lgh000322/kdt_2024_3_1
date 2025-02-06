package shop.shopBE.domain.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortingOption {
    POPULAR("인기순"),
    BEST_SELLERS("판매량순"),
    RECOMMENDED("추천순"),
    NEW_PRODUCT("신상품(입고)순"),
    LOW_PRICE("낮은 가격순"),;

    private final String description;
}
