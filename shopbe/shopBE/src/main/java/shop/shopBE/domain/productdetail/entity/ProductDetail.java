package shop.shopBE.domain.productdetail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.productdetail.exception.ProductDetailExceptionCustom;
import shop.shopBE.global.exception.custom.CustomException;

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

    public void minusSizeStock(int stock) {
        if(this.sizeStock - stock < 0) {
            throw new CustomException(ProductDetailExceptionCustom.OUT_OF_STOCK);
        }

        this.sizeStock -= stock;
    }

    public void plusSizeStock(int stock) {
        // 사이즈 0으로 초기화 후 입력받은 재고변경
        this.sizeStock = 0;
        this.sizeStock += stock;
    }

    public static ProductDetail createDefaultProductDetail(Product product, int shoesSize, int sizeStock) {
        return ProductDetail.builder()
                .product(product)
                .shoesSize(shoesSize)
                .sizeStock(sizeStock)
                .build();
    }
}
