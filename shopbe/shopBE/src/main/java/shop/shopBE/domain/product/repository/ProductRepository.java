package shop.shopBE.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
