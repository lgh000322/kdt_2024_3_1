package shop.shopBE.domain.product.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.productdetail.response.ProductDetails;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductInformsModelView {

    private Long productId;
    private String productName;
    private String mainImgUrl;
    private List<String> sideImgUrl;  // 사이드 이미지 정보
    private int price;
    private PersonCategory personCategory;
    private int likeCount;
    private LocalDateTime createAt;
    private String description;
    private int totalStock;
    private List<ProductDetails> productDetailsList;  // 사이즈별 id, 사이즈별 재고, 사이즈

}
