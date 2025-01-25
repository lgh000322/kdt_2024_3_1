package shop.shopBE.domain.productdetail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.product.entity.Product;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    int shoesSize;

    int sizeStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public static ProductDetail createDefaultProductDetail(Product product, int shoesSize, int sizeStock) {
        return ProductDetail.builder()
                .product(product)
                .shoesSize(shoesSize)
                .sizeStock(sizeStock)
                .build();
    }
}
