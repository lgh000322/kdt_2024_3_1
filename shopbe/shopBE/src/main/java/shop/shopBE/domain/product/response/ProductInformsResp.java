package shop.shopBE.domain.product.response;

import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;

import java.time.LocalDateTime;

public record ProductInformsResp(
        Long productId,
        String productName,
        Long mainImgId,
        String mainImgUrl,
        int price,
        PersonCategory personCategory,
        SeasonCategory seasonCategory,
        ProductCategory productCategory,
        int likeCount,
        LocalDateTime createAt,
        String description,
        int totalStock
) {
}
