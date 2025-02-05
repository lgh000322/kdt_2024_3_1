package shop.shopBE.domain.product.response;

import lombok.*;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;
import shop.shopBE.domain.productdetail.response.ProductDetails;
import shop.shopBE.domain.productimage.response.ImgInforms;

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
    private Long mainImgId;
    private String mainImgUrl;
    private List<ImgInforms> sideImgInforms;
    private int price;
    private PersonCategory personCategory;
    private SeasonCategory seasonCategory;
    private ProductCategory productCategory;
    private int likeCount;
    private LocalDateTime createAt;
    private String description;
    private int totalStock;
    private List<ProductDetails> productDetailsList;  // 사이즈별 id, 사이즈별 재고, 사이즈

}
