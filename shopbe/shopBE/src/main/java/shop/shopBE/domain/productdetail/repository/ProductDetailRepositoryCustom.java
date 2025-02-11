package shop.shopBE.domain.productdetail.repository;

import shop.shopBE.domain.productdetail.entity.ProductDetail;
import shop.shopBE.domain.productdetail.response.ProductDetails;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepositoryCustom {
    Optional<List<ProductDetails>> findProductDetailsByProductId(Long productId);

    Optional<ProductDetail> findByProductIdAndSize(Long productId, int size);
}
