package shop.shopBE.domain.productdetail.repository;

import shop.shopBE.domain.productdetail.entity.ProductDetail;
import shop.shopBE.domain.productdetail.response.ProductDetails;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepositoryCustom {
    Optional<List<ProductDetails>> findProductDetailsByProductId(Long productId);
<<<<<<< HEAD
    Optional<Integer> findQuantityByProductIdAndSize(Long productId, int size);
=======

    Optional<ProductDetail> findByProductIdAndSize(Long productId, int size);
>>>>>>> 20bd19f28fe0ea88fbdd9f1c904144853b17f387
}
