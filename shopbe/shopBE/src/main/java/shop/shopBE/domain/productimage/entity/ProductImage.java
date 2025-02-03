package shop.shopBE.domain.productimage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.productimage.entity.enums.ProductImageCategory;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originName;

    private String savedName;

    @Enumerated(EnumType.STRING)
    private ProductImageCategory productImageCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public static ProductImage createDefaultProductImage(String originName,
                                                          String savedName,
                                                          ProductImageCategory productImageCategory,
                                                          Product product) {
        return ProductImage.builder()
                .originName(originName)
                .savedName(savedName)
                .product(product)
                .productImageCategory(productImageCategory)
                .build();
    }
}
