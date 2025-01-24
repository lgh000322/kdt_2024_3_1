package shop.shopBE.domain.productdetail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.product.entity.Product;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    int shoesSize;

    int sizeStock;

    public static ProductDetail createDefaultProductDetail(Product product, int shoesSize, int sizeStock) {
        return ProductDetail.builder()
                .product(product)
                .shoesSize(shoesSize)
                .sizeStock(sizeStock)
                .build();
    }
}
