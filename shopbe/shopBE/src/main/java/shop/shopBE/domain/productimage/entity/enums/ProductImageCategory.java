package shop.shopBE.domain.productimage.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductImageCategory {
    MAIN("상품 대표 이미지"),
    SIDE("상품 설명 이미지");

    private final String description;
}
