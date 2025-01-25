package shop.shopBE.domain.product.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonCategory {
    MEN("남성"),
    WOMEN("여성"),
    CHILDREN("아동"),
    ALL_PERSON("전체");

    private final String description;
}
