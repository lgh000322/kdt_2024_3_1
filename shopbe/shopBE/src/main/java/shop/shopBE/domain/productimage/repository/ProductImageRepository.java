package shop.shopBE.domain.productimage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.productimage.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, ProductImageRepositoryCustom {

}
