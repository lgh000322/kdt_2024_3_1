package shop.shopBE.domain.productdetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.productdetail.entity.ProductDetail;

import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
