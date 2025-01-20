package shop.shopBE.domain.product.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductCategory {
    // 신발의 카테고리
    SLIPPERS("슬리퍼"),   // 슬리퍼
    SANDALS("샌들"),    // 샌들
    SNEAKERS("스니커즈"),   // 스니커즈
    RUNNING_SHOES("운동화"), // 운동화
    LOAFERS("로퍼"),;     // 로퍼

    private final String description;
}
